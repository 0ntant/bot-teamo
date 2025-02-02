package app.service.repository;

import app.service.model.DataTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataTagRepository extends JpaRepository<DataTag, Long>
{
    Optional<DataTag> findByTitle(String title);
    boolean existsByTitle(String title);
    List<DataTag> findByTitleIn(List<String> title);
}
