package repository.IT;

import app.redqueen.RedQueen;
import app.redqueen.model.Like;
import app.redqueen.repository.LikeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RedQueen.class)
@Sql(scripts = "classpath:sql-script/dao/LikeDAO-testData.sql")
public class LikeRepositoryIT extends AbstractContainerIT
{
    @BeforeAll
    static void startContainer()
    {
        dbContainer.start();
    }

    @Autowired
    LikeRepository likeRepository;

    @Test
    void findById_returnLike()
    {
        //given
        long likeId = 1L;

        //then
        Optional<Like> like = likeRepository.findById(likeId);

        //expected
        assertNotNull(like);
        assertEquals("name_1", like.get().getName());
    }

    @Test
    void findByNameAndText_returnLike()
    {
        //given
        String likeName = "name_1";
        String likeText = "text_1";

        //then
        Optional<Like> like = likeRepository.findByNameAndText(likeName, likeText);

        //expected
        assertNotNull(like);
        assertEquals(1L, like.get().getId());
    }
}
