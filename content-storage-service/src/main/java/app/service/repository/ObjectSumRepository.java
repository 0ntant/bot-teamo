package app.service.repository;

import app.service.model.ObjectSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectSumRepository extends JpaRepository<ObjectSum, Long> {
}
