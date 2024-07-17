package app.redqueen.repository;

import app.redqueen.model.ResidencePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidencePlaceRepository extends JpaRepository<ResidencePlace, Long>
{
    List<ResidencePlace> findByActiveTrue();
}
