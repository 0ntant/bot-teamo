package service.network.module;

import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.database.UserServiceDataFacade;
import app.redqueen.service.network.ResultOrError;
import app.redqueen.service.network.UserTeamoNetDbServiceDecorator;
import app.redqueen.service.network.UserTeamoNetworkService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class UserTeamoNetDbServiceDecoratorMockBlock
{
    @Mock
    private UserTeamoNetworkService userTeamoNetworkService;

    @Mock
    private UserTeamoRepository userTeamoService;

    @Mock
    private UserServiceDataFacade userServiceFacade;

    @Spy
    @InjectMocks
    private UserTeamoNetDbServiceDecorator userTeamoNetDbServiceDecorator;

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
        Mockito.doReturn(clientUser).when(userTeamoNetworkService).getClientUser();
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
        Mockito.verify(userTeamoNetDbServiceDecorator).botBlock(resultOrError);
        Mockito.verify(userTeamoService, Mockito.times(1)).save(clientUser);
    }


    @Test
    void getUserFullInfoById_returnClientBlocked()
    {
        //given
        long userId = 1L ;
        Mockito.doReturn(Optional.empty()).when(userTeamoService).findById(userId);
        Mockito.doReturn(ResultOrError.errorResponse(userTeamoBlock))
                .when(userTeamoNetworkService).getUserFullInfoById(userId);

        //then
        resultOrError
                = userTeamoNetDbServiceDecorator.getUserFullInfoById(userId);
        //expect
            //db
        Mockito.verify(userTeamoService, Mockito.times(1)).findById(userId);
    }


    @Test
    void getSelfFullInfo_returnClientBlocked()
    {
        //given
        Mockito.doReturn(ResultOrError.errorResponse(userTeamoBlock))
                .when(userTeamoNetworkService).getSelfFullInfo();
       // Mockito.doReturn(clientUser).when(userTeamoNetworkService).getClientUser();

        //then
        resultOrError = userTeamoNetDbServiceDecorator.getSelfFullInfo();

        //expect
    }

    @Test
    void setLikeToUserById_returnClientBlocked()
    {
        //given
        int userId = 1;
        Mockito.doReturn(ResultOrError.errorResponse(userTeamoBlock))
                .when(userTeamoNetworkService).setLikeToUserById(userId);

        //then
        resultOrError = userTeamoNetDbServiceDecorator.setLikeToUserById(userId);

        //expected
    }

    @Test
    void getUsersByMessages_returnClientBlocked()
    {
        //given
        Mockito.doReturn(ResultOrError.errorResponse(userTeamoBlock))
                .when(userTeamoNetworkService).getUsersByMessages(1, 20);

        //then
        resultOrError = userTeamoNetDbServiceDecorator.getUsersByMessages(1, 20);

        //expected
    }

    @Test
    void getAllUsersFromGuests_returnClientBlocked()
    {
        //given
        Mockito.doReturn(ResultOrError.errorResponse(userTeamoBlock))
                .when(userTeamoNetworkService).getAllUsersFromGuests(1, 20);

        //then
        resultOrError = userTeamoNetDbServiceDecorator.getAllUsersFromGuests(1, 20);

        //expected
    }


    @Test
    void getUsersFromMatchList_returnClientBlocked()
    {
        //given
        Mockito.doReturn(ResultOrError.errorResponse(userTeamoBlock))
                .when(userTeamoNetworkService).getUsersFromMatchList(1, 20);

        //then
        resultOrError = userTeamoNetDbServiceDecorator.getUsersFromMatchList(1, 20);

        //expected
    }
}
