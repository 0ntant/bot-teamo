package app.service.service;

import app.service.exception.EntityAlreadyExists;
import app.service.model.DataTag;
import app.service.model.TextData;
import app.service.repository.TextDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TextDataService
{
    @Autowired
    DataTagService tagService;

    @Autowired
    TextDataRepository textRepository;

    public TextData getRandomByTagTitle(String tagTitle)
    {
        List<TextData> textData = findByTagTitle(tagTitle);

        if (textData.isEmpty())
        {
            noSuchElementException("TextData tag=%s not found"
                    .formatted(tagTitle));
        }

        return textData.get(
                new Random().nextInt(
                        textData.size()
                )
        );
    }

    public List<TextData> findLike(
            String textContent,
            List<DataTag> tags
    )
    {
        Set<TextData> dataText = new HashSet<>();
        List<DataTag> dbTags = tagService.findIn(
                tags.stream()
                    .map(DataTag::getTitle)
                    .toList()
        );
        List<String> requests = requestVariants(textContent);

        for (String request: requests)
        {
            dataText.addAll(
                    textRepository.findByTextContentContainingAndTags(
                            request,
                            dbTags
                    )
            );
        }

        return new ArrayList<>(dataText);
    }

    public List<TextData> findLike(String textContent)
    {
        Set<TextData> dataText = new HashSet<>();
        List<String> requests = requestVariants(textContent);

        for(String request : requests)
        {
            dataText.addAll(textRepository.findByTextContentContaining(request));
        }

        return new ArrayList<>(dataText);
    }

    private List<String> requestVariants(String request)
    {
        List<String> requests = new ArrayList<>();

        requests.add(request.toLowerCase());
        requests.add(request.toUpperCase());
        requests.add(getFirstUpperCase(getFirstUpperCase(request)));

        return requests;
    }

    private String getFirstUpperCase(String str)
    {
        return str.substring(0, 1).toUpperCase()
                + str.substring(1);
    }

    @Transactional
    public void save(TextData textDataToSave)
    {
        if (isExists(textDataToSave.getTextContent()))
        {
            entityAlreadyExistsException(
                    "TextData=%s already exists".formatted(
                            textDataToSave.getTextContent()
                    )
            );
        }
        attachTags(textDataToSave, textDataToSave.getTags());
        textRepository.save(textDataToSave);
    }

    private void attachTags(TextData textDataToAttachTags, List<DataTag> dataTagsToAttach)
    {
        textDataToAttachTags.setTags(new ArrayList<>());
        for (DataTag textTag : dataTagsToAttach)
        {
            DataTag textDataTag;
            if (tagService.existsByTitle(textTag.getTitle()))
            {
                textDataTag = tagService.findByTitle(textTag.getTitle());
            }
            else
            {
                textDataTag = DataTag.builder()
                        .title(textTag.getTitle().toLowerCase())
                        .textData(new ArrayList<>())
                        .build();
            }
            textDataToAttachTags.getTags().add(textDataTag);
        }
    }

    public List<TextData> findByTagTitle(String tagTitle)
    {
        return tagService.findByTitle(tagTitle).getTextData();
    }

    public boolean isExists(String textContent)
    {
        return textRepository.existsByTextContent(textContent);
    }

    public void delete(long id)
    {
        if (!isExists(id))
        {
            noSuchElementException("Text id=%s not exits".formatted(id));
        }

        textRepository.delete(find(id));
    }

    public TextData find(long id)
    {
        return textRepository.findById(id)
                .orElseThrow(() -> noSuchElementException(
                                "TextData id=%s not found".formatted(id)
                ));
    }

    public boolean isExists(long id)
    {
        return textRepository.existsById(id);
    }

    private NoSuchElementException noSuchElementException(String message)
    {
        throw new NoSuchElementException(message);
    }

    private EntityAlreadyExists entityAlreadyExistsException(String message)
    {
        throw new EntityAlreadyExists(message);
    }
}
