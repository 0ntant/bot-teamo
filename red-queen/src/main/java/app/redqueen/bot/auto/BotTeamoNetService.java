package app.redqueen.bot.auto;

import app.redqueen.model.BotUserData;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.StatisticsTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.network.MessageTeamoNetworkService;
import app.redqueen.service.network.ResultOrError;
import app.redqueen.service.network.StatisticsTeamoNetworkServiceImpl;
import app.redqueen.service.network.UserTeamoNetworkService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@NoArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BotTeamoNetService
{
    @Setter
    @Getter
    UserTeamo userBot;

    @Setter
    private StatisticsTeamoNetworkServiceImpl statTeamoNetworkServ;

    @Setter
    private UserTeamoNetworkService userNetServ;

    @Setter
    private MessageTeamoNetworkService msgNetServ;

    public void sendLike(UserTeamo userToSendLike)
    {
        log.info("Send like to user id={}",
                userToSendLike.getId());
        ResultOrError<String> resultOrError = userNetServ.setLikeToUserById(Math.toIntExact(userToSendLike.getId()));
        resultCheck(resultOrError);
    }

    public List<UserTeamo> getUserLikeList()
    {
        log.info("Get users from like list");
        ResultOrError<List<UserTeamo>> resultOrError
                = userNetServ.getUsersFromMatchList(1, 10);

        resultCheck(resultOrError);
        return resultOrError.getResult();
    }

    public List<UserTeamo> getUsersToSendMessage()
    {
        log.info("Get users to send message");
        ResultOrError<List<UserTeamo>>
                usersFromChatsToWriteMessages
                = userNetServ.getUsersByMessages(1, 30);
        resultCheck(usersFromChatsToWriteMessages);

        return usersFromChatsToWriteMessages.getResult();
    }

    public MessageTeamo sendMessageToUser(UserTeamo receiver, String message)
    {
        ResultOrError <MessageTeamo> resultOrError =
                msgNetServ.sendMessage(userBot, receiver, message);
        resultCheck(resultOrError);

        return resultOrError.getResult();
    }

    public List<MessageTeamo> getMessagesFromPartner(UserTeamo partner)
    {
        ResultOrError<List<MessageTeamo>> resultOrError
                = msgNetServ.getShareableMessages(userBot, partner, 30 , 0);

        resultCheck(resultOrError);
        List<MessageTeamo> messages = resultOrError.getResult();

        log.info("Has chat with={} messages", messages.size());
        return messages;
    }


    public boolean botHasLastMessage(UserTeamo userToSend)
    {
        ResultOrError<List<MessageTeamo>> resultOrError
                = msgNetServ.getShareableMessages(userBot, userToSend, 20 , 0);
        resultCheck(resultOrError);
        List<MessageTeamo> messages = resultOrError.getResult();

        log.info("Has chat with={} messages", messages.size());
        return !messages.isEmpty() &&
                messages.get(messages.size() - 1).getUserSender().equals(userBot);
    }

    public BotUserData getStatistic()
    {
        ResultOrError<StatisticsTeamo> resultOrError =  statTeamoNetworkServ.getStatistic();
        resultCheck(resultOrError);
        BotUserData botUserData = BotUserData.builder()
                .userId(userBot.getId())
                .city(userBot.getCity())
                .name(userBot.getName())
                .sysCreateDate(userBot.getSysCreateDate())
                .statisticsTeamo(resultOrError.getResult())
                .build();
        return botUserData;
    }

    public void resultCheck(ResultOrError<?> resultOrError)
    {
        if (resultOrError.isErrorResponse())
        {
            log.error("Response is error={}",
                    resultOrError.getBlock().getReason());
            throw new RuntimeException("User banned");
        }
    }
}
