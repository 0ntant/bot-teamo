package app.redqueen.dto.input.botPhrase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhraseDto
{
    String body;
    String gender;
    TypeDto type;
}
