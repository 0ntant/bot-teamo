package app.redqueen.repository;

import app.redqueen.model.LifestyleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LifestyleTypeRepository extends JpaRepository<LifestyleType, Long>
{
    @Query("""
            SELECT l FROM LifestyleType l
            WHERE l.label=:label
            """)
    Optional<LifestyleType> findByLabel(@Param("label") String label);
}
