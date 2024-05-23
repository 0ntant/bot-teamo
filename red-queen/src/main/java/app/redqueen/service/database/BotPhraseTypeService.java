package app.redqueen.service.database;

import app.redqueen.model.BotPhraseType;
import app.redqueen.repository.BotPhraseRepository;
import app.redqueen.repository.BotPhraseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BotPhraseTypeService
{
    @Autowired
    BotPhraseTypeRepository botPhraseTypeRep;

    public List<BotPhraseType> findAll()
    {
        return botPhraseTypeRep.findAll();
    }

    public boolean isExitsById(long id)
    {
        return botPhraseTypeRep.findById(id)
                .isPresent();
    }

    public BotPhraseType findById(long id)
    {
        return botPhraseTypeRep.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("BotPhraseType id %s doesn't exists", id)
                ));
    }

    public boolean isExitsByTitle(String title)
    {
        return botPhraseTypeRep.findByTitle(title)
                .isPresent();
    }

    public BotPhraseType findByTitle(String title)
    {
        return botPhraseTypeRep.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("BotPhraseType title %s doesn't exists", title)
                ));
    }

    public void save(BotPhraseType botPhraseType)
    {
        botPhraseTypeRep.save(botPhraseType);
    }
}
