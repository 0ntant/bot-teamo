package app.redqueen.dto.output;


import app.redqueen.model.BotPhrase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BotPhraseDto
{
    long id;
    String body;
    String gender;
    String typeTitle;

    public static BotPhraseDto mapToBotPhraseTypeDto(BotPhrase botPhrase)
    {
        return BotPhraseDto.builder()
                .id(botPhrase.getId())
                .body(botPhrase.getBody())
                .gender(botPhrase.getGender())
                .typeTitle(botPhrase.getBotPhraseType().getTitle())
                .build();
    }
}
