package app.service.repository;

import app.service.model.DataTag;
import app.service.model.TextData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextDataRepository extends JpaRepository<TextData, Long>
{
    boolean existsByTextContent(String textContent);
    List<TextData> findByTextContentContaining(String textContent);
    List<TextData> findByTextContentContainingAndTags(String textContent, List<DataTag>tags);
}
