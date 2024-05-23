package app.redqueen.service.network;

import app.redqueen.model.UserTeamo;
import app.redqueen.repository.BotPhraseRepository;
import app.redqueen.repository.MessageTeamoRepository;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.database.UserServiceDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageNetServiceFactory
{
    @Autowired
    UserTeamoRepository userTeamoRepository;

    @Autowired
    BotPhraseRepository botPhraseRepository;

    @Autowired
    MessageTeamoRepository messageTeamoRepository;

    @Autowired
    UserServiceDataFacade userServiceDataFacade;

    public MessageTeamoNetDbServiceDecorator createMessageNetService(UserTeamo userTeamo)
    {
        return new MessageTeamoNetDbServiceDecorator(
                new MessageTeamoNetworkServiceImpl(userTeamo),
                userTeamoRepository,
                messageTeamoRepository,
                botPhraseRepository,
                userServiceDataFacade
        );
    }

    public MessageTeamoNetDbServiceDecorator createMessageNetService
            (MessageTeamoNetworkService messageTeamoNetworkService)
    {
        return new MessageTeamoNetDbServiceDecorator(
                messageTeamoNetworkService,
                userTeamoRepository,
                messageTeamoRepository,
                botPhraseRepository,
                userServiceDataFacade
        );
    }
}
