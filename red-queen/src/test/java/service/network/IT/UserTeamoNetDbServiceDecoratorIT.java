package service.network.IT;


import app.redqueen.RedQueen;
import app.redqueen.model.Lifestyle;
import app.redqueen.model.UserTeamo;
import app.redqueen.repository.UserTeamoRepository;

import app.redqueen.service.network.ResultOrError;
import app.redqueen.service.network.UserNetServiceFactory;
import app.redqueen.service.network.UserTeamoNetDbServiceDecorator;
import app.redqueen.service.network.UserTeamoNetworkServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import repository.IT.AbstractContainerIT;
import service.network.IT.apiMock.FacesClientFileMock;
import service.network.IT.apiMock.TeamoClientFileMock;
import service.network.IT.apiMock.UserClientFileMock;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RedQueen.class)
@Sql(scripts = "classpath:sql-script/dao/UserTeamoDAO-testData.sql")
public class UserTeamoNetDbServiceDecoratorIT extends AbstractContainerIT
{

    private final UserTeamo client = UserTeamo.builder()
            .id(21593632L)
            .token("3f1ad635d352102121593632")
            .name("21593632")
            .sysCreateDate(new Date())
            .blackListsOwners(new ArrayList<>())
            .email("wijep28580@ubinert.com")
            .password("wijep28580@ubinert.com")
            .build();

    private UserTeamoNetworkServiceImpl userTeamoNetworkService =  new UserTeamoNetworkServiceImpl(client);

    @Autowired
    private UserTeamoRepository userTeamoService;

    @Autowired
    private UserNetServiceFactory userNetServiceFactory;

    @Autowired
    private UserTeamoNetDbServiceDecorator userTeamoNetDbServiceDecorator;

    @BeforeEach
    void changeClientApi()
    {
        userTeamoNetDbServiceDecorator = userNetServiceFactory.createUserNetDecorator(userTeamoNetworkService);

        userTeamoNetworkService.setUserClient(new UserClientFileMock());
        userTeamoNetworkService.setTeamoClient(new TeamoClientFileMock());
        userTeamoNetworkService.setFacesClient(new FacesClientFileMock());
    }

    @Test
    @Transactional
    void getUserFullInfoById_savedInDB()
    {
      //given
      long userId =  21008613;

      //then
      ResultOrError<UserTeamo> resultOrError = userTeamoNetDbServiceDecorator.getUserFullInfoById(userId);
      Optional<UserTeamo> userTeamoOptional =  userTeamoService.findById(userId);
      UserTeamo userTeamo  = userTeamoOptional.get();

      //expected
      assertEquals(userTeamo.getId(), resultOrError.getResult().getId());

      System.out.print(userTeamo.getBlackList().size());

      assertEquals(13, userTeamo.getLifestyleList().size());

      for (Lifestyle lifestyle : userTeamo.getLifestyleList())
      {

          System.out.print(lifestyle.getBody());
          System.out.println(lifestyle.getLifestyleType().getTitle());
      }
      assertEquals(8, userTeamo.getGeneralAttributes().size());
      assertEquals(7, userTeamo.getDislikeList().size());
      assertEquals(4, userTeamo.getUserLookings().size());
      assertEquals(1, userTeamo.getPhotos().size());
    }


    @Test
    void getSelfFullInfo_savedInDB()
    {
        //given
        //then
        ResultOrError<UserTeamo> resultOrError = userTeamoNetDbServiceDecorator.getSelfFullInfo();
        //expected
    }

    @Test
    void setLikeToUserById_success()
    {
        //given
        long userId =  18645281;
        //then
        ResultOrError<String> stringResultOrError = userTeamoNetDbServiceDecorator.setLikeToUserById((int)userId);

        //expected
        assertEquals("Success",stringResultOrError.getResult());
    }

    @Test
    void getUsersByMessages_savedInDB()
    {
        //given

        //then
        ResultOrError<List<UserTeamo>> resultOrError = userTeamoNetDbServiceDecorator.getUsersByMessages(1, 19);
        //expect
    }


    @Test
    void getAllUsersFromGuests()
    {
        //given
        //then
        ResultOrError<List<UserTeamo>> resultOrError = userTeamoNetDbServiceDecorator.getAllUsersFromGuests(1, 20);
        //expect
    }

    @Test
    void getUsersFromMatchList()
    {
        //given

        //then

        ResultOrError<List<UserTeamo>> resultOrError = userTeamoNetDbServiceDecorator.getUsersFromMatchList(1, 20);

        //expect
    }

    @Test
    @Transactional
    void testBlock_getBlockedUser()
    {
        //given
        long userId= 21992192;
        //then
        UserTeamo userTeamo = userTeamoNetDbServiceDecorator.getUserFullInfoById(userId).getResult();

        UserTeamo userTeamoDb = userTeamoService.findById(userId).get();

        //expect
        assertEquals(userTeamo.getId(), userTeamoDb.getId());
        assertEquals(client.getId(), userTeamoDb.getBlackList().get(0).getId());
        assertEquals(client.getId(), userTeamo.getBlackList().get(0).getId());

        assertEquals(userId, client.getBlackListsOwners().get(0).getId());
    }

    @Test
    @Transactional
    void getUserFullInfo_returnBlockUser_savedIdDb()
    {
        //given
        long userId= 21111111;

        //then
        ResultOrError<UserTeamo> resultOrError = userTeamoNetDbServiceDecorator.getUserFullInfoById(userId);

        //expect
        assertTrue(resultOrError.isErrorResponse());
    }

    @Test
    void serialUsersSave_saveInDb()
    {
        //given
        File usersFolder = new File("src/test/resources/apiTeamoResponses/users");

        //then
        for (File userId : usersFolder.listFiles())
        {
            userTeamoNetDbServiceDecorator.getUserFullInfoById(Long.valueOf(userId.getName()));
        }
        //expect

    }
}
