package app.redqueen.bot.auto;

import app.redqueen.model.BotPhrase;
import app.redqueen.model.BotPhraseType;
import app.redqueen.model.BotUserData;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.BotPhraseTypeService;
import app.redqueen.service.database.BotServiceFacade;
import app.redqueen.service.database.DialogGenerator;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.MessageNetServiceFactory;
import app.redqueen.service.network.StatisticsTeamoNetworkServiceImpl;
import app.redqueen.service.network.UserNetServiceFactory;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class BotScheduler implements Runnable
{
    @Autowired
    BotServiceFacade botServiceFacade;

    @Autowired
    private MessageNetServiceFactory messageNetServiceFactory;

    @Autowired
    private UserNetServiceFactory userNetServiceFactory;

    @Autowired
    private UserTeamoService userTeamoService;

    @Getter
    String schedulerState ;

    @Autowired
    BotPhraseTypeService botPhraseTypeService;

    @Autowired
    DialogGenerator dialogGenerator;

    @Autowired
    BotPhraseTypeService botPhraseTypeServ;

    List<BotTeamoComp> botTeamoList = new ArrayList<>();

    private final AtomicBoolean running = new AtomicBoolean(false);

    private Thread thread;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    CopyOnWriteArrayList<String> defaultDialogTypeTitles = new CopyOnWriteArrayList<>( List.of (
            "joke",
            "get_contact",
            "apologize"
    ));

    @Override
    public void run()
    {
        running.set(true);
        logger.info("{} started", this.thread.getName());
        while (isRunning())
        {
            Optional<UserTeamo> botToCreate = getNotBlockedUserWithTokensNotInSchList();
            waitingSec(45, 120);
            if (botToCreate.isPresent())
            {
                addAndStartBot(botToCreate.get());
                waitingSec(100, 200);
            }
            else
            {
                logger.info("No bots in database");
                waitingSec(200, 300);
            }

            List<BotTeamoComp> botsToRemove = createTerminatedBotsList();
            if (!botsToRemove.isEmpty())
            {
                removeFromBotList(botsToRemove);
            }
            waitingSec(45, 80);
        }
        //clear resources
        for (BotTeamoComp botTeamo : botTeamoList)
        {
            logger.info("Scheduler {} stop bot {} id thread",
                    thread.getName(),
                    botTeamo.getUserBot().getId());
            botTeamo.stop();
        }
        botTeamoList.clear();

        logger.info("{} stopped", thread.getName());
    }

    private Optional<UserTeamo> getNotBlockedUserWithTokensNotInSchList ()
    {
        schedulerState = "get user from db";
        List<UserTeamo> notBlockedUsersWithTokens = userTeamoService.findUsersWithTokenAndNotBlocking();
        Random random = new Random();
        notBlockedUsersWithTokens = notBlockedUsersWithTokens
                .stream()
                .filter(userTeamo ->
                                botTeamoList
                                        .stream()
                                        .noneMatch(teamobot -> Objects.equals(
                                                teamobot.getUserBot().getId(),
                                                userTeamo.getId()
                                        ))
                        )
                .toList();

        if (!notBlockedUsersWithTokens.isEmpty())
        {
           return Optional.of(
                   notBlockedUsersWithTokens
                           .get(random.nextInt(notBlockedUsersWithTokens.size()))
           );
        }
        return Optional.empty();
    }

    private Optional<UserTeamo> _getNotBlockedUserWithTokensNotInSchList ()
    {
        schedulerState = "get user from db";
        List<UserTeamo> notBlockedUsersWithTokens = userTeamoService.findUsersWithTokenAndNotBlocking();
        logger.info("Users in pool {}", botTeamoList.size());
        for (UserTeamo userWithToken : notBlockedUsersWithTokens)
        {
            Optional<BotTeamoComp> userInPoolBots = botTeamoList.stream()
                    .filter(bot -> Objects.equals(bot.getUserBot().getId(), userWithToken.getId()))
                    .findAny();
            if (userInPoolBots.isEmpty())
            {
                return Optional.of(userWithToken);
            }
        }
        return Optional.empty();
    }

    public void addAndStartBot(UserTeamo userTeamo)
    {
        schedulerState = "Add start bot";

        List<BotPhraseType> phraseTypeList = new ArrayList<>();

        for (String dialogTitle : defaultDialogTypeTitles)
        {
            try
            {
                BotPhraseType botPhraseTypeCheck
                        = botPhraseTypeService.findByTitle(dialogTitle);
                phraseTypeList.add(botPhraseTypeCheck);
            }
            catch (NoSuchElementException ex)
            {
                ex.printStackTrace();
            }
        }
        BotTeamoDBService botTeamoDBService = new BotTeamoDBService();

        botTeamoDBService.setPhraseTypeList(phraseTypeList);
        botTeamoDBService.setUserBot(userTeamo);
        botTeamoDBService.setBotServiceFacade(botServiceFacade);

        BotTeamoNetService botNetServ = new BotTeamoNetService();
        botNetServ.setUserBot(userTeamo);
        botNetServ.setMsgNetServ(messageNetServiceFactory.createMessageNetService(userTeamo));
        botNetServ.setUserNetServ(userNetServiceFactory.createUserNetDecorator(userTeamo));
        botNetServ.setStatTeamoNetworkServ(new StatisticsTeamoNetworkServiceImpl(userTeamo));

        BotTeamoComp botTeamoComp = BotTeamoComp.builder()
                .userBot(userTeamo)
                .dbService(botTeamoDBService)
                .netService(botNetServ)
                .messagesToUsers(new HashMap<>())
                .build();

        botTeamoList.add(botTeamoComp);
        botTeamoComp.start();
    }

    private List<BotTeamoComp> createTerminatedBotsList()
    {
        schedulerState = "create terminated bots List";
        List<BotTeamoComp> terminatedList = new ArrayList<>();

        for (BotTeamoComp bot : botTeamoList)
        {
            if (bot.getThread().getState().equals(Thread.State.TERMINATED))
            {
                terminatedList.add(bot);
            }
        }
        return terminatedList;
    }

    private void removeFromBotList(List<BotTeamoComp> botsToRemove)
    {
        schedulerState = "removing terminated bots";
        for(BotTeamoComp botToRemove : botsToRemove)
        {
            botTeamoList.remove(botToRemove);
        }
    }

    public int getUsersInPoolSize()
    {
        return botTeamoList.size();
    }

    public List<BotUserData> getListBotUserDataFromActiveBots()
    {
        List<BotUserData> botUserDataList = new ArrayList<>();
        for (BotTeamoComp botTeamoComp : botTeamoList)
        {
            botUserDataList.add(botTeamoComp.getStatistic());
        }
        return botUserDataList;
    }

    public void start()
    {
        thread = new Thread(this, "BotScheduler");
        thread.start();
    }

    public boolean isRunning()
    {
        return running.get();
    }

    public void stop()
    {
        running.set(false);
        for (BotTeamoComp botTeamo : botTeamoList)
        {
            botTeamo.stop();
        }
        botTeamoList.clear();
        thread.interrupt();
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
            logger.info("Scheduler {} wait {} sec", thread.getName(), sec);
            Thread.sleep(1000 * sec);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    public List<BotPhraseType> getDefaultDialogTypeTitles()
    {
        return defaultDialogTypeTitles.stream()
                .map(botPhraseType -> botPhraseTypeService.findByTitle(botPhraseType))
                .toList();
    }

    public void setDefaultDialogTypeTitles(List<BotPhraseType> botPhraseTypes)
    {
        this.defaultDialogTypeTitles = new CopyOnWriteArrayList<>(botPhraseTypes
        .stream()
        .map(botTypePhrase -> botPhraseTypeServ
                .findById(botTypePhrase.getId())
                .getTitle())
        .toList());
        for (BotTeamoComp botTeamo : botTeamoList)
        {
            botTeamo.setBotPrasesType(defaultDialogTypeTitles
                    .stream()
                    .map(botPhraseTypeTitle -> botPhraseTypeServ.findByTitle(botPhraseTypeTitle))
                    .toList());
        }
    }
}
