package app.service.service;

import app.service.model.DataTag;
import app.service.repository.DataTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DataTagService
{
    @Autowired
    DataTagRepository tagRepository;

    public DataTag findByTitle(String title)
    {
        return tagRepository.findByTitle(title)
                .orElseThrow(()
                        -> new NoSuchElementException(
                                "Tag with title=%s not exists"
                                        .formatted(title)
                ));
    }

    public List<DataTag> findIn(List<String> titles)
    {
        return tagRepository.findByTitleIn(
                titles
                        .stream()
                        .map(String::toLowerCase)
                        .toList()
        );
    }

    public boolean existsByTitle(String dataTagTitle)
    {
        return tagRepository.existsByTitle(
                dataTagTitle.toLowerCase()
        );
    }
}
