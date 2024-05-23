package service.network.IT;


import app.redqueen.Main;
import app.redqueen.model.BotPhrase;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.repository.BotPhraseRepository;
import app.redqueen.repository.MessageTeamoRepository;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.network.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import repository.IT.AbstractContainerIT;
import service.network.IT.apiMock.UserClientFileMock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Sql(scripts = "classpath:sql-script/messageTeamoNetDbServiceDecorator-testData.sql")
public class MessageTeamoNetDbServiceDecoratorIT extends AbstractContainerIT
{

    private final UserTeamo client = UserTeamo.builder()
            .id(21593632L)
            .token("3f1ad635d352102121593632")
            .name("Антон")
            .email("wijep28580@ubinert.com")
            .password("wijep28580@ubinert.com")
            .sysCreateDate(new Date())
            .build();

    private final UserTeamo partner = UserTeamo.builder()
            .id(21596288L)
            .name("Аня")
            .build();

    @Autowired
    MessageNetServiceFactory messageNetServiceFactory;

    private UserTeamoNetworkService userNetServ;


    @Autowired
    private UserNetServiceFactory userNetServiceFactory;

    @Autowired
    BotPhraseRepository botPhraseRepository;

    @Autowired
    UserTeamoRepository userTeamoRepository;

    @Autowired
    private MessageTeamoRepository messageTeamoService;

    private MessageTeamoNetworkServiceImpl messageTeamoNetworkService = new MessageTeamoNetworkServiceImpl(client);

    private MessageTeamoNetDbServiceDecorator messageTeamoNetDbServiceDecorator;


    @BeforeEach
    void changeClientApi()
    {

        messageTeamoNetDbServiceDecorator = messageNetServiceFactory.createMessageNetService(messageTeamoNetworkService);
        userNetServ = userNetServiceFactory.createUserNetDecorator(client);

        messageTeamoNetworkService.setUserClient(new UserClientFileMock());
    }

    @Test
    void getShareableMessages_savedInDB()
    {
        //given

        //then
        ResultOrError <List<MessageTeamo>> resultOrError
                = messageTeamoNetDbServiceDecorator.getShareableMessages(client, partner, 1, 20);

        //expect
        for(MessageTeamo msg : resultOrError.getResult())
        {
            System.out.println(msg.getBody());
        }
    }

    @Test
    void sendMessage_savedInDB()
    {
        //given
        String message = "SomeMSG";

        //then
        ResultOrError < MessageTeamo> resultOrError
                = messageTeamoNetDbServiceDecorator.sendMessage(client, partner, message);
        Optional<MessageTeamo> msg  = messageTeamoService.findByBodyAndCreateDate(resultOrError.getResult().getBody(),
                resultOrError.getResult().getCreateDate() );

        //expect
        assertEquals(resultOrError.getResult().getId(), msg.get().getId());
        assertEquals(message, resultOrError.getResult().getBody());
    }

    @Test
    @Transactional
    void sendMessage_savedInDB_addBotPhrase()
    {
        //given
        String message = "MessageToJoke";

        //then
        ResultOrError <MessageTeamo> resultOrError
                = messageTeamoNetDbServiceDecorator.sendMessage(client, partner, message);

            //db check
        MessageTeamo msg  = messageTeamoService.findByBodyAndCreateDate(resultOrError.getResult().getBody(),
                resultOrError.getResult().getCreateDate()).get();
        BotPhrase botPhrase = botPhraseRepository.findByBody(message).get();

        //expect
        assertEquals(resultOrError.getResult().getId(), msg.getId());
        assertEquals(message, resultOrError.getResult().getBody());
        assertEquals(message,botPhrase.getBotPhraseType().getMessageList().get(0).getBody());
    }

    @Test
    @Transactional
    void sendMessage_userBlock()
    {
        //given
        String message = "blockMe";

        //then
        ResultOrError <MessageTeamo> resultOrError
                = messageTeamoNetDbServiceDecorator.sendMessage(client, partner, message);
        UserTeamo blockedUser = userTeamoRepository.findById(client.getId()).get();

        //expect
        assertTrue(resultOrError.isErrorResponse());
        assertSame(client.getId(), blockedUser.getId());
        assertTrue(blockedUser.getBlock().getIsBlocking());
    }
}
