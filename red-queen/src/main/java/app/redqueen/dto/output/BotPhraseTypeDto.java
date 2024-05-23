package app.redqueen.dto.output;

import app.redqueen.model.BotPhraseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BotPhraseTypeDto
{
    long id;
    String title;

    public static BotPhraseTypeDto mapToBotPhraseType(BotPhraseType botPhraseType)
    {
        return new BotPhraseTypeDto(botPhraseType.getId(), botPhraseType.getTitle());
    }
}
