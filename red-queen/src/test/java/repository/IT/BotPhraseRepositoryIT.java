package repository.IT;

import app.redqueen.Main;
import app.redqueen.model.BotPhrase;
import app.redqueen.repository.BotPhraseRepository;
import app.redqueen.repository.UserTeamoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@Sql(scripts = "classpath:sql-script/dao/BotPharaseDAO-testData.sql")
public class BotPhraseRepositoryIT extends AbstractContainerIT
{
    @Autowired
    BotPhraseRepository botPhraseRepository;

    @Autowired
    UserTeamoRepository userTeamoRepository;

    @BeforeAll
    static void startContainer()
    {
        dbContainer.start();
    }

    @Test
    void findById_returnBotPhrase()
    {
        //given
        long botPhraseId = 1L;

        //then
        Optional<BotPhrase> botPhrase = botPhraseRepository.findById(botPhraseId);
        //expect
        assertTrue(botPhrase.isPresent());
        assertEquals("body_1",botPhrase.get().getBody());
        assertEquals("female",botPhrase.get().getGender());
    }

    @Test
    void findByBotPhraseTypeTitle_returnListBotPhrase()
    {
        //given
        String botPhraseTypeTitle = "Title_type_4";

        //then
        List<BotPhrase> botPhrases = botPhraseRepository.findByBotPhraseTypeTitle(botPhraseTypeTitle);
        //expect
        assertNotNull(botPhrases);
        assertEquals(1, botPhrases.size());
        assertEquals("body_7", botPhrases.get(0).getBody());
        assertEquals(botPhraseTypeTitle, botPhrases.get(0).getBotPhraseType().getTitle());
    }

    @Test
    void findByBody_returnBotPhrase()
    {
        //given
        String botPhraseBody = "body_1";

        //then
        Optional<BotPhrase> botPhraseOpt = botPhraseRepository.findByBody(botPhraseBody);
        //expect
        assertTrue(botPhraseOpt.isPresent());

        BotPhrase botPhrase = botPhraseOpt.get();

        assertEquals("body_1",botPhrase.getBody());
        assertEquals(1L,botPhrase.getId());
        assertEquals("female",botPhrase.getGender());
    }


    @Test
    void findInBody_returnListBotPhraseList()
    {
        //given
        List<String> phasesBodies = List.of("body_1", "body_2", "body_3", "body_error");

        //then
        List<BotPhrase> phrases = botPhraseRepository.findInBody(phasesBodies);

        //expect
        assertNotNull(phrases);
        assertEquals(3, phrases.size());
    }

    @Test
    void findInBodyAndBotPhraseTypeTitle_returnListBotPhraseList()
    {
        //given
        String botPhraseTypeTitle = "Title_type_3";
        List<String> phasesBodies = List.of("body_1", "body_4", "body_5", "body_error");

        //then
        List<BotPhrase> phrases = botPhraseRepository.findInBodyAndBotPhraseTypeTitle(phasesBodies, botPhraseTypeTitle);

        //expect
        assertNotNull(phrases);
        assertEquals(2, phrases.size());
    }

}
