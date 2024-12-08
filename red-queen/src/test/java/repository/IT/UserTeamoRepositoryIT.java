package repository.IT;

import app.redqueen.RedQueen;
import app.redqueen.model.UserTeamo;
import app.redqueen.repository.UserTeamoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = RedQueen.class)
@Sql(scripts = "classpath:sql-script/dao/UserTeamoDAO-testData.sql")
public class UserTeamoRepositoryIT extends AbstractContainerIT
{

    @Autowired
    UserTeamoRepository userTeamoRepository;


    @BeforeAll
    static void startDBContainer()
    {
        dbContainer.start();
    }

    @Test
    void findUserById_returnUserTeamo()
    {
        //gieven
        long userId = 1L;

        //then
        Optional<UserTeamo> userTeamo = userTeamoRepository.findById(userId);

        //expected
        assertEquals("Аня", userTeamo.get().getName());
    }

    @Test
    void findChatPartners_returnUserTeamoList()
    {
        //given
        UserTeamo userTeamo = UserTeamo.builder()
                .id(2L)
                .build();

        //then
        List<UserTeamo> partners
                = userTeamoRepository.findChatPartners(userTeamo);

        //expected
        assertNotNull(partners);
        assertEquals(2, partners.size());
    }

    @Test
    void findUserWithTokenAndNotBlocking_returnUserTeamo()
    {
        //given

        //then

        List<UserTeamo> userTeamoList = userTeamoRepository.findUserWithTokenAndNotBlocking();

        //expected
        assertNotNull(userTeamoList);
        assertEquals(3, userTeamoList.size());
    }

    @Transactional
    @Test
    void checkUserInBlackList_returnUserTeamo()
    {
        //given
        long userId= 1L;
        long userIdInBlackList = 2L;

        //then
        UserTeamo userTeamo = userTeamoRepository.findById(userId).get();

        //expect
        assertEquals(2, userTeamo.getBlackList().size());
        assertEquals(userIdInBlackList, userTeamo.getBlackList().get(0).getId());

        assertEquals(1, userTeamo.getBlackList().get(0).getBlackListsOwners().size());
        assertEquals(userId, userTeamo.getBlackList().get(0).getBlackListsOwners().get(0).getId());

    }

    @Transactional
    @Test
    void addUserToBlacklist_saveInDB()
    {
        //given
        long userId= 1L;
        long userAddingToBlacklistId = 4L;

        //then
        UserTeamo userTeamo = userTeamoRepository.findById(userId).get();
        UserTeamo userTeamoAddingToBlacklist
                = userTeamoRepository.findById(userAddingToBlacklistId).get();

        userTeamo.getBlackList().add(userTeamoAddingToBlacklist);
        userTeamoAddingToBlacklist.getBlackListsOwners().add(userTeamo);

        //expect
        userTeamoRepository.save(userTeamo);
    }

}
