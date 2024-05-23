package app.redqueen.repository;

import app.redqueen.model.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, Long>
{
    @Query("""
            SELECT d from Dislike d
            WHERE d.name=:name and d.text=:text
            """)
    Optional<Dislike> findByNameAndText(@Param("name") String name,
                                        @Param("text") String text);
}
