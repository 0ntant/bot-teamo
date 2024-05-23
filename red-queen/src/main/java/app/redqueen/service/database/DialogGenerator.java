package app.redqueen.service.database;

import app.redqueen.model.BotPhrase;
import app.redqueen.model.BotPhraseType;
import app.redqueen.repository.BotPhraseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class DialogGenerator
{
    @Autowired
    BotPhraseRepository botPhraseRepository;

    public List<BotPhrase> genrateFromTypeList(List<BotPhraseType> botPhraseTypes)
    {
        List<BotPhrase> genDialog = new ArrayList<>();
        Random randomIndex = new Random();
        for (BotPhraseType botPhraseType : botPhraseTypes)
        {
            List<BotPhrase> botPhrases = botPhraseRepository.findByBotPhraseTypeTitle(botPhraseType.getTitle());
            genDialog.add(botPhrases.get(
                    randomIndex.nextInt(0 , botPhrases.size())
            ));
        }
        log.info("Generated dialog");
        for (BotPhrase phrase : genDialog)
        {
            log.info(phrase.getBody());
        }
        return genDialog;
    }

    public List<BotPhrase> genrateFromTypeList(List<BotPhraseType> botPhraseTypes, String gender)
    {
        List<BotPhrase> genDialog = new ArrayList<>();
        Random randomIndex = new Random();
        for (BotPhraseType botPhraseType : botPhraseTypes)
        {
            List<BotPhrase> botPhrases
                    = botPhraseRepository
                    .findByBotPhraseTypeTitleAndGender(
                            botPhraseType.getTitle(),
                            gender
                    );
            genDialog.add(botPhrases.get(
                    randomIndex.nextInt(0 , botPhrases.size())
            ));
        }
        log.info("Generated dialog");
        for (BotPhrase phrase : genDialog)
        {
            log.info(phrase.getBody());
        }
        return genDialog;
    }
}
