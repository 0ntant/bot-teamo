package app.redqueen.dto.output;

import app.redqueen.model.Lifestyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LifestyleDto
{
    private long id;
    private String body;

    public static LifestyleDto mapToLifestyleDTO(Lifestyle lifestyle)
    {
        return new LifestyleDto(lifestyle.getId(), lifestyle.getBody());
    }
}
