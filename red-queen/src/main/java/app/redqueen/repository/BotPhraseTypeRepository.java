package app.redqueen.repository;

import app.redqueen.model.BotPhraseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BotPhraseTypeRepository extends JpaRepository<BotPhraseType, Long>
{
    @Query("""
            SELECT b FROM BotPhraseType b
            WHERE b.title=:title 
            """)
    Optional<BotPhraseType> findByTitle(@Param("title") String title);
}
