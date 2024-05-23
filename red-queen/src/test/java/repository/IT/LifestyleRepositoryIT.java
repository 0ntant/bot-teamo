package repository.IT;

import app.redqueen.Main;
import app.redqueen.model.Lifestyle;
import app.redqueen.repository.LifestyleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@Sql(scripts = "classpath:sql-script/dao/LifestyleDAO-testData.sql")
public class LifestyleRepositoryIT extends AbstractContainerIT
{
    @BeforeAll
    static void startDBContainer()
    {
        dbContainer
                .start();
    }

    @Autowired
    LifestyleRepository lifestyleRepository;

    @Test
    void findById_returnLifestyle()
    {
        //given
        long lifestyleId = 1L;

        //then
        Optional<Lifestyle> lifestyleOpt = lifestyleRepository.findById(lifestyleId);

        //expected
        assertTrue(lifestyleOpt.isPresent());
        Lifestyle lifestyle = lifestyleOpt.get();

        assertEquals("body_1", lifestyle.getBody());

    }
}
