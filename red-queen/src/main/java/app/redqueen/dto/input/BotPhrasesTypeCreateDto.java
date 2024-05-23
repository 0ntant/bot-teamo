package app.redqueen.dto.input;

import app.redqueen.model.BotPhraseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BotPhrasesTypeCreateDto
{
    String title;

    public static BotPhrasesTypeCreateDto mapToBotPhrasesTypeCreateDto
            (BotPhraseType botPhraseType)
    {
        return new BotPhrasesTypeCreateDto(botPhraseType.getTitle());
    }
}
