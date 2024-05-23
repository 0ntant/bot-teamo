package repository.IT;

import app.redqueen.Main;
import app.redqueen.model.BotPhraseType;
import app.redqueen.repository.BotPhraseTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Main.class)
@Sql(scripts = "classpath:sql-script/dao/BotPhraseTypeDAO-testData.sql")
public class BotPhraseTypeRepositoryIT extends AbstractContainerIT
{

    @BeforeAll
    static void startDBContainer()
    {
       // dbContainer.withInitScript("sql-script/dao/BotPhraseTypeDAO-testData.sql");
        dbContainer.start();
    }

    @Autowired
    BotPhraseTypeRepository botPhraseTypeRepository;


    @Test
    void findByTitle_returnBotPhraseType()
    {
        //given
        String botPhraseTypeTitle = "Title_type_1";

        //then
        Optional<BotPhraseType> botPhraseTypeOpt = botPhraseTypeRepository.findByTitle(botPhraseTypeTitle);

        //expect
        assertTrue(botPhraseTypeOpt.isPresent());
        BotPhraseType botPhraseType = botPhraseTypeOpt.get();

        Assertions.assertEquals(botPhraseTypeTitle, botPhraseType.getTitle());
    }

    @Test
    void findById_returnBotPhraseType()
    {
        //given
        long botPhraseTypeId = 1L;

        //then
        Optional<BotPhraseType> botPhraseTypeOpt = botPhraseTypeRepository.findById(botPhraseTypeId);

        //expect
        assertTrue(botPhraseTypeOpt.isPresent());
        BotPhraseType botPhraseType = botPhraseTypeOpt.get();

        Assertions.assertEquals(botPhraseTypeId, botPhraseType.getId());
    }
}
