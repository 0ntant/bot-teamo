package app.redqueen.repository;

import app.redqueen.model.Lifestyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifestyleRepository extends JpaRepository<Lifestyle, Long>
{
}
