package repository.IT;

import app.redqueen.RedQueen;
import app.redqueen.model.LookingType;
import app.redqueen.repository.LookingTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RedQueen.class)
@Sql(scripts = "classpath:sql-script/dao/LookingTypeDAO-tesData.sql")
public class LookingTypeRepositoryIT extends AbstractContainerIT
{
    @BeforeAll
    static void startDBContainer()
    {
        dbContainer
                .start();
    }

    @Autowired
    LookingTypeRepository lookingTypeRepository;

    @Test
    void findById_returnLookingType()
    {
        //given
        long lookingTypeId = 1L;

        //then
        Optional<LookingType> lookingTypeOpt = lookingTypeRepository.findById(lookingTypeId);

        //expected
        assertTrue(lookingTypeOpt.isPresent());
        LookingType lookingType = lookingTypeOpt.get();

        assertNotNull(lookingType);
        assertEquals("label_1", lookingType.getLabel());
    }

    @Test
    void findByLabel_returnLookingType()
    {
        //given
        String lookingTypeLabel = "label_1";

        //then
        Optional<LookingType> lookingTypeOpt = lookingTypeRepository.findByLabel(lookingTypeLabel);

        //expected
        assertTrue(lookingTypeOpt.isPresent());
        LookingType lookingType = lookingTypeOpt.get();

        assertNotNull(lookingType);
        assertEquals(1L, lookingType.getId());
    }
}
