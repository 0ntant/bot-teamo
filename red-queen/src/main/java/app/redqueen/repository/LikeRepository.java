package app.redqueen.repository;

import app.redqueen.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long>
{
    @Query("""
            SELECT l from Like l 
            WHERE l.name=:name and l.text=:text
            """)
    Optional<Like> findByNameAndText(@Param("name") String name,
                                     @Param("text") String text);
}
