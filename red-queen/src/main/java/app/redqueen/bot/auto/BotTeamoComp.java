package app.redqueen.bot.auto;

import app.redqueen.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@AllArgsConstructor
@Builder
public class BotTeamoComp implements Runnable
{
    @Getter
    @Setter
    private UserTeamo userBot;

    @Setter
    private  BotTeamoDBService dbService;

    private BotTeamoNetService netService;

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Setter
    private Map<Long, Queue<BotPhrase>> messagesToUsers ;

    @Getter
    private Thread thread;

    @Getter
    private String botState ;

    @Override
    public void run()
    {
        botState = "Start bot ";
        log.info("{} botThread={}" ,
                botState,
                thread.getName());
        running.set(true);
        while (isRunning())
        {
            checkPartnersMessages();
            waitingSec(34, 120);

            List<UserTeamo> userToSendMessages = getUsersToWriteMessages();
            waitingSec(45, 110);

            checkMessagesFromUsers(userToSendMessages);
            waitingSec(50, 290);

            generateDialogs(userToSendMessages);
            waitingSec(34, 99);

            sendMessagesFromMap();
            waitingSec(50, 120);

            List<UserTeamo> usersToLike = deleteUserBot(getUserLikeList());

            for(UserTeamo userToLike : usersToLike)
            {
                this.sendLike(userToLike);
                waitingSec(35, 120);
            }

        }
        log.info("{} stop", thread.getName());
    }

    public void checkPartnersMessages()
    {
        botState = "Getting messages from partner";
        List<UserTeamo> partners = dbService.getPartners();

        checkMessagesFromUsers(partners);
    }

    public List<UserTeamo> getUsersToWriteMessages()
    {
        botState = "Get uses to write messages";
        log.info("{}", botState);

        return netService.getUsersToSendMessage();
    }

    private void checkMessagesFromUsers(List<UserTeamo> userList)
    {
        for(UserTeamo user : userList)
        {
            if(dbService.isUserAddBotToBlackList(user))
            {
                log.warn("Bot in black list user id={}",user.getId());
                waitingSec(12, 45);
                continue;
            }
            netService.getMessagesFromPartner(user);
            waitingSec(60, 120);
        }
    }

    public void setBotPrasesType(List<BotPhraseType> botPhraseTypes)
    {
        synchronized (running)
        {
            log.info("{} change dialog", thread.getName());
            try
            {
                //running.wait();
                dbService.setPhraseTypeList(botPhraseTypes);
                List<UserTeamo> usersToWrite = new ArrayList<>();
                for (Map.Entry<Long , Queue<BotPhrase>> dialog: messagesToUsers.entrySet())
                {
                    usersToWrite.add(dbService.getUserById(dialog.getKey()));
                }
                generateDialogs(usersToWrite);
               // running.notify();
            }catch (Exception ex)
            {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }

    public void generateDialogs(List<UserTeamo> usersToWrite)
    {
        botState = "Generate dialogs";
        for (UserTeamo userToWrite : usersToWrite)
        {
            if (!messagesToUsers.containsKey(userToWrite.getId()) &&
                    !dbService.dialogWithUserAlreadyExists(userToWrite))
            {
                log.info("{} for id {}", botState, userToWrite.getId());
                Queue<BotPhrase> queuePhases
                        = new LinkedList<>(dbService.generateDialogForUser(userToWrite));
                messagesToUsers.put(userToWrite.getId(), queuePhases);
            }
        }
    }

    public void sendMessagesFromMap()
    {
        botState = "Sending messages";
        log.info("{}", botState);
        int minDelaySecBetweenSending = 50;
        int maxDelaySecBetweenSending = minDelaySecBetweenSending  + 100;
        List<UserTeamo> keysToRemove = new ArrayList<>();

        for(Map.Entry<Long, Queue<BotPhrase>> messagesToUser: messagesToUsers.entrySet())
        {
            Queue<BotPhrase> messagesQueue= messagesToUser.getValue();
            Long userToSendId = messagesToUser.getKey();
            UserTeamo userToSend =  dbService.getUserById(userToSendId);

            if (messagesQueue.isEmpty())
            {
                keysToRemove.add(userToSend);
                log.info("User id={} get all dialog", userToSend.getId());
            }
            else
            {
                if(dbService.botHasLastMessage(userToSend))
                {
                    continue;
                }
                BotPhrase phraseToSend = messagesQueue.poll();
                log.info("Sending message={} to user id={}",
                        phraseToSend.getBody(),
                        userToSend.getId()
                );
                netService.sendMessageToUser(userToSend, phraseToSend.getBody());
                waitingSec(minDelaySecBetweenSending, maxDelaySecBetweenSending);

                if (dbService.isUserAddBotToBlackList(userToSend))
                {
                    keysToRemove.add(userToSend);
                    log.warn("User id={} add bot id={} to black list",
                            userToSend.getId(),
                            userBot.getId()
                    );
                }
            }
        }

        for (UserTeamo keyToRemove : keysToRemove)
        {
            log.info("Removing user id={} from sendingStructure",
                    keyToRemove.getId());
            messagesToUsers.remove(keyToRemove.getId());
        }
    }

    public List<UserTeamo> deleteUserBot(List<UserTeamo> userTeamoList)
    {
        List<UserTeamo> usersWithoutToken = new ArrayList<>();
        for(UserTeamo userTeamo : userTeamoList)
        {
            if (userTeamo.getToken() == null)
            {
                usersWithoutToken.add(userTeamo);
            }
        }
        return usersWithoutToken;
    }

    public List<UserTeamo> getUserLikeList()
    {
        return netService.getUserLikeList();
    }

    public void sendLike(UserTeamo userToLike)
    {
        netService.sendLike(userToLike);
    }

    public BotUserData getStatistic()
    {
        BotUserData botUserData =  netService.getStatistic();
        botUserData.setState(botState);
        return botUserData;
    }

    public void start()
    {
        thread = new Thread(this,String.format("Bot id=%s", userBot.getId()));
        thread.start();
    }

    public void stop()
    {
        running.set(false);
        log.info("{} stopping ", thread.getName());
        this.thread.interrupt();
    }

    public boolean isRunning ()
    {
        return running.get();
    }

    public void waitingSec(int min, int max)
    {
        if(!isRunning())
        {
            min = 1;
            max = 10;
        }
        Random rand = new Random();
        long sec = rand.nextInt(min, max );
        try
        {
            log.info("Bot id {} wait {} sec", userBot.getId(), sec);
            Thread.sleep(1000 * sec);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }
}
