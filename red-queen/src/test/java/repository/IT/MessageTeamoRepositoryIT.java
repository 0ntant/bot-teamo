package repository.IT;

import app.redqueen.Main;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.repository.MessageTeamoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@Sql(scripts = "classpath:sql-script/dao/MessageTeamoDAO-testData.sql")
public class MessageTeamoRepositoryIT extends AbstractContainerIT
{
    @BeforeAll
    static void startDBContainer()
    {
        dbContainer
                .start();
    }

    @Autowired
    MessageTeamoRepository messageTeamoRepository;


    @Test
    void findChat_returnListTeamoMessages()
    {
        //given
        UserTeamo firstUserTeamo = UserTeamo
                .builder()
                .id(1L)
                .build();
        UserTeamo secondUserTeamo = UserTeamo
                .builder()
                .id(2L)
                .build();
        //then
        List<MessageTeamo> chat
                = messageTeamoRepository.findChat(firstUserTeamo, secondUserTeamo);

        //expected
        assertNotNull(chat);
        assertEquals(3, chat.size());
    }


    @Test
    void findByBodyAndCreateDate_returnTeamoMessage() throws ParseException
    {
        //given
        String body = "body_3";
        Date createDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-03");

        //then
        Optional<MessageTeamo> messageOpt
                = messageTeamoRepository.findByBodyAndCreateDate(body, createDate);

        //expected
        assertTrue(messageOpt.isPresent());
        MessageTeamo message = messageOpt.get();

        assertNotNull(message);
        assertEquals(3L,message.getId());
    }

    @Test
    void findAllMessagesByUser_returnListTeamoMessages()
    {
        //given
        UserTeamo firstUserTeamo = UserTeamo
                .builder()
                .id(1L)
                .build();

        //then
        List<MessageTeamo> messageTeamoList
                = messageTeamoRepository.findAllMessagesByUser(firstUserTeamo);

        //expected
        assertNotNull(messageTeamoList);
        assertEquals(3,messageTeamoList.size());
    }

    @Test
    void findLastMessageByUser_returnTeamoMessage()
    {
        //given
        UserTeamo firstUserTeamo = UserTeamo
                .builder()
                .id(1L)
                .build();

        //then
        Optional<MessageTeamo> messageOpt
                = messageTeamoRepository.findLastMessageByUser(firstUserTeamo);

        //expected
        assertTrue(messageOpt.isPresent());
        MessageTeamo lastMessageByUser = messageOpt.get();

        assertNotNull(lastMessageByUser);
        assertEquals("body_3",lastMessageByUser.getBody());
        assertEquals(3L,lastMessageByUser.getId());
    }


}
