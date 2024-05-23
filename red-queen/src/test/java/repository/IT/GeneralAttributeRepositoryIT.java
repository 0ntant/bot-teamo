package repository.IT;

import app.redqueen.Main;
import app.redqueen.model.GeneralAttribute;
import app.redqueen.repository.GeneralAttributeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = Main.class)
@Sql(scripts = "classpath:sql-script/dao/GeneralAttributeDAO-testData.sql")
public class GeneralAttributeRepositoryIT extends AbstractContainerIT
{
    @BeforeAll
    static void startDBContainer()
    {
        dbContainer
                .start();
    }

    @Autowired
    GeneralAttributeRepository generalAttributeRepository;

    @Test
    void findById_returnGeneralAttribute()
    {
        //given
        long generalAttributeId = 1L;

        //then
        Optional<GeneralAttribute> generalAttributeOpt = generalAttributeRepository.findById(generalAttributeId);

        //expected
        assertTrue(generalAttributeOpt.isPresent());
        GeneralAttribute generalAttribute = generalAttributeOpt.get();


        assertEquals("name_1", generalAttribute.getName());
    }

    @Test
    void findByName_returnGeneralAttribute()
    {
        //given
        String generalAttributeName = "name_1";

        //then
         Optional<GeneralAttribute> generalAttributeOpt = generalAttributeRepository.findByName(generalAttributeName);

        //expected
        assertTrue(generalAttributeOpt.isPresent());
        GeneralAttribute generalAttribute = generalAttributeOpt.get();
        
        assertEquals(1, generalAttribute.getValue());
    }

    @Test
    void findByValueText_returnGeneralAttribute()
    {
        //given
        String generalAttributeValueText = "value_text_1";

        //then
         Optional<GeneralAttribute> generalAttributeOpt = generalAttributeRepository.findByValueText(generalAttributeValueText);

        //expected
        assertTrue(generalAttributeOpt.isPresent());
        GeneralAttribute generalAttribute = generalAttributeOpt.get();

        assertEquals(1, generalAttribute.getValue());
    }

    @Test
    void findByValueTextAndName_returnGeneralAttribute()
    {
        //given
        String generalAttributeValueText = "value_text_1";
        String generalAttributeName = "name_1";

        //then
        Optional<GeneralAttribute> generalAttributeOpt
                = generalAttributeRepository
                .findByValueTextAndName(generalAttributeValueText, generalAttributeName);

        //expected
        assertTrue(generalAttributeOpt.isPresent());
        GeneralAttribute generalAttribute = generalAttributeOpt.get();

        assertEquals(1, generalAttribute.getValue());
    }

}
