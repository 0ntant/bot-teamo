package app.redqueen.service.network;

import app.redqueen.model.BotPhrase;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import app.redqueen.repository.BotPhraseRepository;
import app.redqueen.repository.MessageTeamoRepository;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.database.UserServiceDataFacade;
import app.redqueen.service.database.UserTeamoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("DbMes")
public class MessageTeamoNetDbServiceDecorator
        extends UserServiceDecorator
        implements MessageTeamoNetworkService
{
    private MessageTeamoRepository messageTeamoService;

    private MessageTeamoNetworkService messageTeamoNetworkService;

    private BotPhraseRepository botPhraseRepository;

    private UserServiceDataFacade userServiceDataFacade;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MessageTeamoNetDbServiceDecorator(
                    MessageTeamoNetworkService messageTeamoNetworkService,
                    UserTeamoService userTeamoRepository,
                    MessageTeamoRepository messageTeamoRepository,
                    BotPhraseRepository botPhraseRepository,
                    UserServiceDataFacade userServiceDataFacade
                    )
    {
        super(messageTeamoNetworkService, userTeamoRepository);
        this.messageTeamoNetworkService = messageTeamoNetworkService;
        this.messageTeamoService = messageTeamoRepository;
        this.botPhraseRepository = botPhraseRepository;
        this.userServiceDataFacade = userServiceDataFacade;
    }

    @Override
    public ResultOrError<List<MessageTeamo>> getShareableMessages(
            UserTeamo owner,
            UserTeamo partner,
            int limit,
            int offset)
    {
        ResultOrError<List<MessageTeamo>> resultOrError
                = messageTeamoNetworkService
                    .getShareableMessages(
                            owner,
                            partner,
                            limit,
                            offset);


        if(botBlock(resultOrError))
        {
            logger.warn("User block during getting message");
            return resultOrError;
        }

        if (checkUserDeleteProfile(resultOrError, partner))
        {
            logger.warn("User id={} is delete self profile", partner.getId());
            ResultOrError.successResponse(new ArrayList<>());
            return resultOrError;
        }

        if(checkUserAddedToBlackList(resultOrError, partner))
        {
            userServiceDataFacade.addingBotToBlackList(getClientUser(),partner);
        }

        for (MessageTeamo messageTeamo : resultOrError.getResult())
        {
            Optional<MessageTeamo> messageTeamoInDb =
                    messageTeamoService.findByBodyAndCreateDate(
                            messageTeamo.getBody(),
                            messageTeamo.getCreateDate());
            if (messageTeamoInDb.isEmpty())
            {
                if (Objects.equals(messageTeamo.getUserSender().getId(), owner.getId()))
                {
                    isMessageBotPhrase(messageTeamo);
                }
                messageTeamoService.save(messageTeamo);
            }
        }
        return resultOrError;
    }

    private boolean isMessageBotPhrase(MessageTeamo msgToCheck)
    {
        Optional<BotPhrase> botPhraseOpt = botPhraseRepository.findByBody(msgToCheck.getBody());
        if (botPhraseOpt.isPresent())
        {
            BotPhrase botPhrase = botPhraseOpt.get();
            msgToCheck.setBotPhraseType(botPhrase.getBotPhraseType());
            botPhrase.getBotPhraseType().getMessageList().add(msgToCheck);
        }
        else
        {
            logger.warn(
                    "Message={} not found in botPhrases",
                    msgToCheck.getBody()
            );
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public ResultOrError<MessageTeamo> sendMessage(
            UserTeamo sender,
            UserTeamo receiver,
            String message)
    {
        ResultOrError<MessageTeamo> resultOrError
                = messageTeamoNetworkService.sendMessage(sender, receiver, message);
        if(botBlock(resultOrError))
        {
            logger.warn("User block during sending message");
            return resultOrError;
        }
        MessageTeamo messageToSave = resultOrError.getResult();
        isMessageBotPhrase(messageToSave);
        messageTeamoService.save(messageToSave);
        return resultOrError;
    }
}
