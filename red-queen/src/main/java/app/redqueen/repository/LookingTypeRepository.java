package app.redqueen.repository;

import app.redqueen.model.LookingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LookingTypeRepository extends JpaRepository<LookingType, Long>
{
    Optional<LookingType> findByLabel(String label);
}
