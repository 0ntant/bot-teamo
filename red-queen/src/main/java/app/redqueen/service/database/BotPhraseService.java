package app.redqueen.service.database;

import app.redqueen.model.BotPhrase;
import app.redqueen.repository.BotPhraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BotPhraseService
{
    @Autowired
    BotPhraseRepository botPhraseRep;

    public List<BotPhrase> findAll()
    {
        return botPhraseRep.findAll();
    }

    public boolean isExistsById(long id )
    {
        return botPhraseRep.findById(id)
                .isPresent();
    }

    public boolean isExistsByBody(String body)
    {
        return  botPhraseRep.findByBody(body)
                .isPresent();
    }

    public BotPhrase findById(long id)
    {
        return  botPhraseRep.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("BotPhrase id %s doesn't exits", id)
                ));
    }
    public BotPhrase findByBody(String body)
    {
        return  botPhraseRep.findByBody(body)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("BotPhrase body %s doesn't exits", body)
                ));
    }

    public List<BotPhrase> findByBotPhraseTypeTitle(String botPhraseTypeTitle)
    {
        return botPhraseRep.findByBotPhraseTypeTitle(botPhraseTypeTitle);
    }

    public List<BotPhrase> findInBody(List<String> bodyList)
    {
        return  botPhraseRep.findInBody(bodyList);
    }

    public List<BotPhrase> findInBodyAndBotPhraseTypeTitle(List<String> bodyList, String botPhraseTypeTitle)
    {
        return botPhraseRep.findInBodyAndBotPhraseTypeTitle
                (bodyList, botPhraseTypeTitle);
    }

    public void save(BotPhrase botPhrase)
    {
        botPhraseRep.save(botPhrase);
    }

    public void delete(BotPhrase botPhrase)
    {
        botPhraseRep.delete(botPhrase);
    }
}

