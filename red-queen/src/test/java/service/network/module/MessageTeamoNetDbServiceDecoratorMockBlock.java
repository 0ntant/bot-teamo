package service.network.module;

import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import app.redqueen.repository.MessageTeamoRepository;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.network.MessageTeamoNetDbServiceDecorator;
import app.redqueen.service.network.MessageTeamoNetworkServiceImpl;
import app.redqueen.service.network.ResultOrError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class MessageTeamoNetDbServiceDecoratorMockBlock
{
    @Mock
    private UserTeamoRepository userTeamoService;
    @Mock
    MessageTeamoNetworkServiceImpl messageTeamoNetworkService;

    @Mock
    MessageTeamoRepository messageTeamoService;

    @Spy
    @InjectMocks
    private MessageTeamoNetDbServiceDecorator messageTeamoNetDbServiceDecorator;

    @Mock
    private UserTeamo clientUser ;

    @Mock
    private UserTeamoBlock userTeamoBlock ;

    private ResultOrError<?> resultOrError;

    @BeforeEach
    void init()
    {
        when(userTeamoBlock.getIsBlocking()).thenReturn(true);
        when(userTeamoBlock.getReason()).thenReturn("user is self deleted");
        Mockito.doReturn(clientUser).when(messageTeamoNetworkService).getClientUser();
        Mockito.doReturn(2L).when(clientUser).getId();
    }

    @AfterEach
    void checkUserBlock()
    {
        //expected
        assertTrue(resultOrError.isErrorResponse());
        assertEquals(resultOrError.getBlock(), userTeamoBlock);
        Mockito.verify(clientUser).setBlock(userTeamoBlock);
            //db
        Mockito.verify(userTeamoService, Mockito.times(1)).save(clientUser);
            //NetDecorator
        Mockito.verify(messageTeamoNetDbServiceDecorator).botBlock(resultOrError);
        Mockito.verify(userTeamoService, Mockito.times(1)).save(clientUser);
    }


    @Test
    void getShareableMessages_returnClientBlocked()
    {
        //given
        UserTeamo owner = mock(UserTeamo.class);
        UserTeamo partner = mock(UserTeamo.class);
        Mockito.doReturn(ResultOrError.errorResponse(userTeamoBlock)).when(messageTeamoNetworkService)
                .getShareableMessages(
                        owner,
                        partner,
                        1,
                        10
                );

        //then
        resultOrError = messageTeamoNetDbServiceDecorator.getShareableMessages(
                owner,
                partner,
                1,
                10
        );

        //expect
    }

    @Test
    void sendMessage_returnClientBlocked()
    {
        //given
        UserTeamo sender = mock(UserTeamo.class);
        UserTeamo receiver = mock(UserTeamo.class);
        String msg = "Message to send";
        Mockito.doReturn(ResultOrError.errorResponse(userTeamoBlock))
                .when(messageTeamoNetworkService)
                .sendMessage(
                        sender,
                        receiver,
                        msg
                );

        //then
        resultOrError = messageTeamoNetDbServiceDecorator.sendMessage(
                sender,
                receiver,
                msg
        );

        //expect
    }
}
