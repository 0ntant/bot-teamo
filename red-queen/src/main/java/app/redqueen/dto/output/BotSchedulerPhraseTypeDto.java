package app.redqueen.dto.output;

import app.redqueen.model.BotPhraseType;

public record BotSchedulerPhraseTypeDto(int number, String title, long id, int phraseCount)
{
    public static BotSchedulerPhraseTypeDto map(int number, BotPhraseType botPhraseType)
    {
        return new BotSchedulerPhraseTypeDto(
                number,
                botPhraseType.getTitle(),
                botPhraseType.getId(),
                botPhraseType.getBotPhrases().size()
        );
    }
}
