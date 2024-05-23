package service.network.module;


import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.repository.BotPhraseRepository;
import app.redqueen.repository.BotPhraseTypeRepository;
import app.redqueen.repository.MessageTeamoRepository;
import app.redqueen.service.network.MessageTeamoNetDbServiceDecorator;
import app.redqueen.service.network.MessageTeamoNetworkService;
import app.redqueen.service.network.ResultOrError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageTeamoNetDbServiceDecoratorMock
{
    @Mock
    MessageTeamoNetworkService messageTeamoNetworkService;

    @Mock
    MessageTeamoRepository messageTeamoService;


    @Mock
    BotPhraseRepository botPhraseRepository;

    @Spy
    @InjectMocks
    MessageTeamoNetDbServiceDecorator messageTeamoNetDbServiceDecorator;

    ResultOrError<?> resultOrError;

    @AfterEach
    void checkBlockBot()
    {
        assertTrue(resultOrError.isSuccessResponse());
        verify(messageTeamoNetDbServiceDecorator, Mockito.times(1)).botBlock(resultOrError);
    }

    @Test
    void getShareableMessages_saveInDb() {
        //given
        UserTeamo owner = mock(UserTeamo.class);
        UserTeamo partner = mock(UserTeamo.class);
        List<MessageTeamo> messageTeamoList = new ArrayList<>();

        int messagesCount = 10;
        for (int i = 0; i < messagesCount; i++)
        {
            MessageTeamo messageTeamo = mock(MessageTeamo.class);

            doReturn(new Date()).when(messageTeamo).getCreateDate();
            doReturn(String.valueOf(i)).when(messageTeamo).getBody();

            messageTeamoList.add(messageTeamo);
        }

        doReturn(ResultOrError.successResponse(messageTeamoList))
                .when(messageTeamoNetworkService)
                .getShareableMessages(owner, partner, messagesCount, 0);

        //then
        resultOrError
                = messageTeamoNetDbServiceDecorator
                .getShareableMessages(owner, partner, messagesCount, 0);

        //expect
        assertEquals(messageTeamoList, resultOrError.getResult());

        for(int i = 0; i< messagesCount; i++)
        {
            verify(messageTeamoService, times(1)).save(messageTeamoList.get(i));
            verify(messageTeamoService, times(1))
                    .findByBodyAndCreateDate(String.valueOf(i), messageTeamoList.get(i).getCreateDate());
        }
    }


    @Test
    void sendMessage_saveInDb()
    {
        //given
        UserTeamo sender = mock(UserTeamo.class);
        UserTeamo receiver = mock(UserTeamo.class);
        String msg = "Message to send";
        MessageTeamo messageTeamo = mock(MessageTeamo.class);

        doReturn(Optional.empty()).when(botPhraseRepository)
                .findByBody(msg);
        doReturn(ResultOrError.successResponse(messageTeamo))
                .when(messageTeamoNetworkService)
                .sendMessage(sender, receiver, msg);

        //then
        resultOrError = messageTeamoNetDbServiceDecorator
                .sendMessage(sender, receiver, msg);

        //expected
        Assertions.assertEquals(messageTeamo, resultOrError.getResult());
            //db
        verify(messageTeamoService).save(messageTeamo);
    }

}
