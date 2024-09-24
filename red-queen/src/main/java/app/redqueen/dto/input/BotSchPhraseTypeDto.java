package app.redqueen.dto.input;

import app.redqueen.model.BotPhraseType;

public record BotSchPhraseTypeDto(long id, String title)
{
    public static BotPhraseType map(BotSchPhraseTypeDto botSchPhraseTypeDto)
    {
        BotPhraseType botPhraseType = new BotPhraseType();
        botPhraseType.setId(botSchPhraseTypeDto.id);
        botPhraseType.setTitle(botSchPhraseTypeDto.title);
        return botPhraseType;
    }
}
