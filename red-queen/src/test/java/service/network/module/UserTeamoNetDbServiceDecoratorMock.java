package service.network.module;

import app.redqueen.model.UserTeamo;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.database.UserServiceDataFacade;
import app.redqueen.service.network.ResultOrError;
import app.redqueen.service.network.UserTeamoNetDbServiceDecorator;
import app.redqueen.service.network.UserTeamoNetworkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserTeamoNetDbServiceDecoratorMock
{
    @Mock
    UserTeamoNetworkService userTeamoNetworkService;

    @Mock
    UserTeamoRepository userTeamoService;

    @Mock
    private UserServiceDataFacade userServiceFacade;

    @Spy
    @InjectMocks
    UserTeamoNetDbServiceDecorator userTeamoNetDbServiceDecorator;

    @Mock
    UserTeamo clientUser ;

    @Mock
    UserTeamo teamoUser ;

    @Test
    void getUserFullInfoById_returnTeamoUserFromAPI_saveInDb()
    {
        //given
        long userId= 1L;
        Mockito.doReturn(Optional.empty()).when(userTeamoService).findById(userId);
        Mockito.doReturn(ResultOrError.successResponse(teamoUser)).when(userTeamoNetworkService).getUserFullInfoById(userId);
        Mockito.doReturn(teamoUser).when(userServiceFacade).synchronizeDbSave(teamoUser);
        //then
        ResultOrError<UserTeamo> resultOrError
                = userTeamoNetDbServiceDecorator.getUserFullInfoById(userId);
        //expect
        assertTrue(resultOrError.isSuccessResponse());
        assertEquals(resultOrError.getResult(), teamoUser);
            //db
        Mockito.verify(userTeamoService, Mockito.times(1)).findById(userId);
        Mockito.verify(userTeamoService, Mockito.times(1)).save(teamoUser);
            //dbFacade
        Mockito.verify(userServiceFacade, Mockito.times(1)).synchronizeDbSave(teamoUser);
            //NetDecorator
        Mockito.verify(userTeamoNetDbServiceDecorator).botBlock(resultOrError);
    }


    @Test
    void getSelfFullInfo_returnTeamoUserFromAPI_savedInDB()
    {
        //given
        Mockito.doReturn(ResultOrError.successResponse(clientUser))
                .when(userTeamoNetworkService).getSelfFullInfo();
        Mockito.doReturn(clientUser)
                .when(userServiceFacade).synchronizeDbSave(clientUser);

        //then
        ResultOrError<UserTeamo> resultOrError
                = userTeamoNetDbServiceDecorator.getSelfFullInfo();

        //expected
        assertTrue(resultOrError.isSuccessResponse());
        assertEquals(resultOrError.getResult(), clientUser);
            //db
        Mockito.verify(userTeamoService, Mockito.times(1)).save(clientUser);
            //dbFacade
        Mockito.verify(userServiceFacade, Mockito.times(1)).synchronizeDbSave(clientUser);
            //NetDecorator
        Mockito.verify(userTeamoNetDbServiceDecorator).botBlock(resultOrError);

    }

    @Test
    void setLikeToUserById_returnSuccessString()
    {
        //given
        String successMessage  = "Success";
        int userId = 1;
        Mockito.doReturn(ResultOrError.successResponse(successMessage))
                .when(userTeamoNetworkService).setLikeToUserById(userId);
        //then
        ResultOrError<String> resultOrError = userTeamoNetDbServiceDecorator.setLikeToUserById(userId);

        //expect
        assertTrue(resultOrError.isSuccessResponse());
        assertEquals("Success",resultOrError.getResult());
            //network
        Mockito.verify(userTeamoNetworkService, Mockito.times(1)).setLikeToUserById(userId);

            //decorator
        Mockito.verify(userTeamoNetDbServiceDecorator).botBlock(resultOrError);
    }

    @Test
    void getUsersByMessages_returnedUsers_savedInDB()
    {
        //given
        List<UserTeamo> usersFromMessages = new ArrayList<>();
        Random random = new Random();
        final int usersGenCount = random.nextInt(20 - 10 + 1);
        for (int i = 0 ; i < usersGenCount; i++)
        {
            UserTeamo userTeamo = mock(UserTeamo.class);
            when(userTeamo.getId()).thenReturn((long)i);
            usersFromMessages.add(userTeamo);

            Mockito.doReturn(ResultOrError.successResponse(userTeamo))
                    .when(userTeamoNetworkService)
                    .getUserFullInfoById(i);

            Mockito.doReturn(userTeamo)
                    .when(userServiceFacade).synchronizeDbSave(userTeamo);

        }
        Mockito
                .doReturn(ResultOrError.successResponse(usersFromMessages))
                .when(userTeamoNetworkService).getUsersByMessages(1, usersGenCount);

        //then
        ResultOrError<List<UserTeamo>> resultOrError
                = userTeamoNetDbServiceDecorator.getUsersByMessages(1, usersGenCount);

        //expect
        assertTrue(resultOrError.isSuccessResponse());
        assertEquals(resultOrError.getResult(), usersFromMessages);
        assertEquals(resultOrError.getResult().size(), usersFromMessages.size());

        for (UserTeamo userTeamo : usersFromMessages)
        {
            Mockito.verify(userTeamoService, Mockito.times(1))
                    .findById(userTeamo.getId());

            Mockito.verify(userTeamoNetworkService, Mockito.times(1))
                    .getUserFullInfoById(userTeamo.getId());

            //db
            Mockito.verify(userTeamoService, Mockito.times(1))
                            .save(userTeamo);

            //dbFacade
            Mockito.verify(userServiceFacade, Mockito.times(1))
                    .synchronizeDbSave(userTeamo);

            Mockito.verify(userTeamoNetDbServiceDecorator, Mockito.times(1))
                    .botBlock(ResultOrError.successResponse(userTeamo));
        }

        Mockito.verify(userTeamoNetDbServiceDecorator, Mockito.times(1))
                .botBlock(resultOrError);

    }

    @Test
    void getAllUsersFromGuests_returnedUsers_calledGetUserFullInfoById()
    {
        //given
        List<UserTeamo> usersFromGuests = new ArrayList<>();
        Random random = new Random();
        final int usersGenCount = random.nextInt(20 - 10 + 1);
        for (int i = 0 ; i < usersGenCount; i++)
        {
            UserTeamo userTeamo = mock(UserTeamo.class);
            when(userTeamo.getId()).thenReturn((long)i);
            usersFromGuests.add(userTeamo);

            Mockito.doReturn(ResultOrError.successResponse(userTeamo))
                    .when(userTeamoNetworkService)
                    .getUserFullInfoById(i);

            Mockito.doReturn(userTeamo)
                    .when(userServiceFacade).synchronizeDbSave(userTeamo);
        }
        Mockito.doReturn(ResultOrError.successResponse(usersFromGuests))
                .when(userTeamoNetworkService)
                .getAllUsersFromGuests(1, usersGenCount);


        //then
        ResultOrError<List<UserTeamo>> resultOrError
                = userTeamoNetDbServiceDecorator.getAllUsersFromGuests(1, usersGenCount);

        //expect
        assertTrue(resultOrError.isSuccessResponse());
        assertEquals(resultOrError.getResult(), usersFromGuests);
        assertEquals(resultOrError.getResult().size(), usersFromGuests.size());

        for (UserTeamo userTeamo : usersFromGuests)
        {
            Mockito.verify(userTeamoService, Mockito.times(1))
                    .findById(userTeamo.getId());

            Mockito.verify(userTeamoNetworkService, Mockito.times(1))
                    .getUserFullInfoById(userTeamo.getId());

            Mockito.verify(userTeamoService, Mockito.times(1))
                    .save(userTeamo);

            Mockito.verify(userServiceFacade, Mockito.times(1))
                    .synchronizeDbSave(userTeamo);

            Mockito.verify(userTeamoNetDbServiceDecorator, Mockito.times(1))
                    .botBlock(ResultOrError.successResponse(userTeamo));
        }

        Mockito.verify(userTeamoNetDbServiceDecorator, Mockito.times(1))
                .botBlock(resultOrError);
    }

    @Test
    void getUsersFromMatchList_returnedUsers_calledGetUserFullInfoById()
    {
        //given
        List<UserTeamo> usersFromMatchList = new ArrayList<>();
        Random random = new Random();
        final int usersGenCount = random.nextInt(20 - 10 + 1);
        for (int i = 0 ; i < usersGenCount; i++)
        {
            UserTeamo userTeamo = mock(UserTeamo.class);
            when(userTeamo.getId()).thenReturn((long)i);
            usersFromMatchList.add(userTeamo);

            Mockito.doReturn(ResultOrError.successResponse(userTeamo))
                    .when(userTeamoNetworkService)
                    .getUserFullInfoById(i);

            Mockito.doReturn(userTeamo)
                    .when(userServiceFacade).synchronizeDbSave(userTeamo);
        }
        Mockito.doReturn(ResultOrError.successResponse(usersFromMatchList))
                .when(userTeamoNetworkService)
                .getUsersFromMatchList(1, usersGenCount);

        //then
        ResultOrError<List<UserTeamo>> resultOrError
                = userTeamoNetDbServiceDecorator.getUsersFromMatchList(1, usersGenCount);

        //expect
        assertTrue(resultOrError.isSuccessResponse());
        assertEquals(resultOrError.getResult(), usersFromMatchList);
        assertEquals(resultOrError.getResult().size(), usersFromMatchList.size());

        for (UserTeamo userTeamo : usersFromMatchList)
        {
            Mockito.verify(userTeamoService, Mockito.times(1))
                    .findById(userTeamo.getId());

            Mockito.verify(userTeamoNetworkService, Mockito.times(1))
                    .getUserFullInfoById(userTeamo.getId());

            Mockito.verify(userTeamoService, Mockito.times(1))
                    .save(userTeamo);

            Mockito.verify(userTeamoNetDbServiceDecorator, Mockito.times(1))
                    .botBlock(ResultOrError.successResponse(userTeamo));

            Mockito.verify(userServiceFacade, Mockito.times(1))
                    .synchronizeDbSave(userTeamo);
        }

        Mockito.verify(userTeamoNetDbServiceDecorator, Mockito.times(1))
                .botBlock(resultOrError);
    }

}
