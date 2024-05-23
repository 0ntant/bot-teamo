package app.redqueen.bot.auto;

import app.redqueen.model.BotPhrase;
import app.redqueen.model.BotPhraseType;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.DialogGenerator;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.MessageTeamoNetworkService;
import app.redqueen.service.network.ResultOrError;
import app.redqueen.service.network.UserTeamoNetworkService;
import lombok.*;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
@Scope(value = "prototype")
public class BotTeamo implements Runnable
{
    @Getter
    @Setter
    private UserTeamo userBot;

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Setter
    private UserTeamoNetworkService userNetServ;

    @Setter
    private MessageTeamoNetworkService msgNetServ;

    @Setter
    @Getter
    private List<BotPhraseType> phraseTypeList;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private Map<UserTeamo, Queue<BotPhrase>> messagesToUsers ;

    @Getter
    private Thread thread;

    @Getter
    private String botState ;

    @Setter
    private UserTeamoService userTeamoService;

    @Setter
    private DialogGenerator dialogGenerator;


    public void resultCheck(ResultOrError<?> resultOrError)
    {
        botState = "Check response";
        if (resultOrError.isErrorResponse())
        {
            logger.error("Response is error={}",
                    resultOrError.getBlock().getReason());
            stop();
            thread.interrupt();
        }
    }

    public void sendMessagesFromMap()
    {
        botState = "Sending messages";
        logger.info("{}", botState);
        int delaySecBetweenSending = 50;
        List<UserTeamo> keysToRemove = new ArrayList<>();

        for(Map.Entry<UserTeamo, Queue<BotPhrase>> messagesToUser: messagesToUsers.entrySet())
        {
            Queue <BotPhrase> messagesToSend = messagesToUser.getValue();
            UserTeamo userToSend = messagesToUser.getKey();

            if (messagesToSend.isEmpty())
            {
                keysToRemove.add(userToSend);
            }
            else
            {
                //check Last message is not bot's message
                ResultOrError<List<MessageTeamo>> resultOrError
                        = msgNetServ.getShareableMessages(userBot, userToSend, 20 , 0);
                resultCheck(resultOrError);
                List<MessageTeamo> messages = resultOrError.getResult();

                logger.info("Has chat with={} messages", messages.size());

                if(!messages.isEmpty() && messages.get(messages.size() - 1).getUserSender().equals(userBot))
                {
                    continue;
                }

                //try to send message
                String messageBody =  messagesToSend.poll().getBody();
                logger.info("Sending user id={} message={}",
                        userToSend.getId(),
                        messageBody);
                ResultOrError<MessageTeamo> messageFromApi = msgNetServ.sendMessage(
                        userBot,
                        userToSend,
                        messageBody);
                waitingSec(delaySecBetweenSending);
                resultCheck(messageFromApi);

                if (isUserAddBotToBlackList(userToSend))
                {
                    keysToRemove.add(userToSend);
                }
            }
        }

        for (UserTeamo keyToRemove : keysToRemove)
        {
            logger.info("Removing user from sendingStructure");
            messagesToUsers.remove(keyToRemove);
        }
    }

    @Transactional
    public boolean isUserAddBotToBlackList(UserTeamo blackListOwner)
    {
        boolean checkUserExist = userTeamoService.isExistById(blackListOwner.getId());
        boolean userBotInBlackList;
        if(!checkUserExist)
        {
            logger.error("User id={} not found in DB", blackListOwner.getId());
            return true;
        }
        UserTeamo userBlackListOwner = userTeamoService.findById(blackListOwner.getId());

        userBotInBlackList = userBlackListOwner.getBlackList().contains(userBot);
        if (userBotInBlackList)
        {
            logger.warn("User id={} added bot user id={} to blacklist",
                    blackListOwner.getId(),
                    userBot.getId());
        }
        return userBotInBlackList;
    }

    @Transactional
    public void prepareDialogsToUsers(List<UserTeamo> usersToWrite)
    {
        botState = "Preparing dialog";
        logger.info("{}", botState);
        //check bot already send messageStructure
        List<UserTeamo> partnersInDB = userTeamoService.findChatPartners(userBot);
        for (UserTeamo userToWrite : usersToWrite)
        {
            if (!partnersInDB.contains(userToWrite) &&
                !messagesToUsers.containsKey(userToWrite))
            {
                ResultOrError<List<MessageTeamo>> messages
                        = msgNetServ.getShareableMessages(userBot,userToWrite, 20 , 0);
                resultCheck(messages);

                synchronized (phraseTypeList)
                {
                    List<BotPhrase> dialog
                            = dialogGenerator.genrateFromTypeList(phraseTypeList);
                    messagesToUsers.put(userToWrite, new LinkedList<>(dialog));
                }
            }
        }
    }

    public void sendLike(UserTeamo userToSendLike)
    {
        botState = "Sending like";
        logger.info("{}", botState);
        logger.info("Send like to user id={}",
                userToSendLike.getId());
        ResultOrError<String> resultOrError = userNetServ.setLikeToUserById(Math.toIntExact(userToSendLike.getId()));
        resultCheck(resultOrError);
    }

    public List<UserTeamo> getUserLikeList()
    {
        botState = "Getting user to like";
        logger.info("{}", botState);
        ResultOrError<List<UserTeamo>> resultOrError
                = userNetServ.getUsersFromMatchList(1, 10);

        resultCheck(resultOrError);
        return resultOrError.getResult();
    }

    @Transactional
    public List<UserTeamo> getUsersToSendMessage()
    {
        botState = "Getting users to send messages";
        logger.info("{}", botState);
        ResultOrError<List<UserTeamo>>
             usersFromChatsToWriteMessages
                = userNetServ.getUsersByMessages(1, 30);
        resultCheck(usersFromChatsToWriteMessages);

        return usersFromChatsToWriteMessages.getResult();
    }

    public void start()
    {
        thread = new Thread(this,String.format("Bot id=%s", userBot.getId()));
        thread.start();
    }

    public void stop()
    {
        running.set(false);
    }

    public boolean isRunning ()
    {
        return running.get();
    }

    @Override
    public void run()
    {
        botState = "Started";
        Random rand = new Random();
        logger.info("{} botThread={}" ,
                botState,
                thread.getName());
        running.set(true);
        while (isRunning())
        {
            List<UserTeamo> userToSendMessages = getUsersToSendMessage();
            waitingSec(rand.nextInt(45, 110));

            prepareDialogsToUsers(userToSendMessages);
            waitingSec(rand.nextInt(34, 99));

            sendMessagesFromMap();
            waitingSec(rand.nextInt(50, 120));

            List<UserTeamo> usersToLike = getUserLikeList();
            waitingSec(rand.nextInt(30 , 120));

            for(UserTeamo userToLike : usersToLike)
            {
                this.sendLike(userToLike);
                waitingSec(rand.nextInt(35, 120));
            }

        }

        logger.info("{} stopper", thread.getName());
    }

    public void waitingSec(long sec)
    {
        try
        {
            logger.info("Bot id {} wait {} sec", userBot.getId(), sec);
            Thread.sleep(1000 * sec);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

//    synchronized public List<BotPhraseType> getPhraseTypeList() {
//        return phraseTypeList;
//    }
//
//    synchronized public void setMessagesToUsers(Map<UserTeamo, Queue<BotPhrase>> messagesToUsers)
//    {
//        this.messagesToUsers = messagesToUsers;
//    }
}
