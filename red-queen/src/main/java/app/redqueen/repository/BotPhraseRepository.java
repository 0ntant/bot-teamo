package app.redqueen.repository;

import app.redqueen.model.BotPhrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotPhraseRepository extends JpaRepository<BotPhrase, Long>
{
    @Query("""
            SELECT b FROM BotPhrase b
                LEFT JOIN b.botPhraseType as t
            WHERE t.title=:title
            """)
    List<BotPhrase> findByBotPhraseTypeTitle(@Param("title") String botPhraseTypeTitle);

    @Query("""
            SELECT b FROM BotPhrase b
            WHERE b.body=:body
            """)
    Optional<BotPhrase> findByBody(@Param("body") String body);

    @Query("""
            SELECT b FROM BotPhrase b
            WHERE b.body in (:bodyList)
            """)
    List<BotPhrase> findInBody(@Param("bodyList") List<String> bodyList);

    @Query("""
            SELECT b FROM BotPhrase b
                LEFT JOIN b.botPhraseType as t
            WHERE t.title=:title and b.body in (:bodyList)
            """)
    List<BotPhrase> findInBodyAndBotPhraseTypeTitle(@Param("bodyList") List<String> bodyList,
                                                    @Param("title") String botPhraseTypeTitle);
    @Query("""
            SELECT b FROM BotPhrase b
                LEFT JOIN b.botPhraseType as t
            WHERE t.title=:title and b.gender=:gender
            """)
    List<BotPhrase> findByBotPhraseTypeTitleAndGender(@Param("title") String botPhraseTypeTitle,
                                                      @Param("gender") String gender);
}
