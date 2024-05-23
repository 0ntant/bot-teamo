package repository.IT;

import app.redqueen.Main;
import app.redqueen.model.LifestyleType;
import app.redqueen.repository.LifestyleTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@Sql(scripts = "classpath:sql-script/dao/LifestyleTypeDAO-testData.sql")
public class LifestyleTypeRepositoryIT  extends AbstractContainerIT
{
    @BeforeAll
    static void startDBContainer()
    {
        dbContainer
                .start();
    }

    @Autowired
    LifestyleTypeRepository lifestyleTypeRepository;

    @Test
    void findById_returnLifestyleType()
    {
        //given
        long lifestyleId = 1L;

        //then
        Optional<LifestyleType> lifestyleTypeOpt = lifestyleTypeRepository.findById(lifestyleId);

        //expect
        assertTrue(lifestyleTypeOpt.isPresent());

        LifestyleType lifestyleType = lifestyleTypeOpt.get();

        assertNotNull(lifestyleType);
        assertEquals("label_1", lifestyleType.getLabel());
    }

    @Test
    void findByLabel_returnLifestyleType()
    {
        //given
        String lifestyleLabel = "label_1";

        //then
        Optional<LifestyleType> lifestyleTypeOpt = lifestyleTypeRepository.findByLabel(lifestyleLabel);

        //expect
        assertTrue(lifestyleTypeOpt.isPresent());

        LifestyleType lifestyleType = lifestyleTypeOpt.get();

        assertNotNull(lifestyleType);
        assertEquals(1, lifestyleType.getId());
    }
}
