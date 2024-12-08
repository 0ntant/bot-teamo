package repository.IT;

import app.redqueen.RedQueen;
import app.redqueen.model.Dislike;
import app.redqueen.repository.DislikeRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = RedQueen.class)
@Sql(scripts = "classpath:sql-script/dao/DislikeDAO-testData.sql")
public class DislikeRepositoryIT extends AbstractContainerIT
{
    @BeforeAll
    static void startDBContainer()
    {
        dbContainer
                .start();
    }

    @Autowired
    DislikeRepository dislikeRepository;


    @Test
    void findById_returnDislike()
    {
        //given
        long dislikeId = 1L;

        //then
        Optional<Dislike> dislikeOpt = dislikeRepository.findById(dislikeId);

        //expected
        assertTrue(dislikeOpt.isPresent());
        Dislike dislike = dislikeOpt.get();

        assertEquals("text_1", dislike.getText());
    }

    @Test
    void findByNameAndText_returnDislike()
    {
        //given
        String dislikeName = "name_1";
        String dislikeText = "text_1";

        //then
        Optional<Dislike> dislikeOpt = dislikeRepository.findByNameAndText(dislikeName, dislikeText);

        //expected
        assertTrue(dislikeOpt.isPresent());
        Dislike dislike = dislikeOpt.get();

        assertEquals(dislikeText, dislike.getText());
        assertEquals(dislikeName, dislike.getName());
        assertEquals(1L, dislike.getId());
    }
}
