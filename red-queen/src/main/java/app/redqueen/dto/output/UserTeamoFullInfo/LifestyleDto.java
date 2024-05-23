package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.Lifestyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class LifestyleDto
{
    String lifestyleTypeTitle;
    String lifestyleBody;

    static LifestyleDto mapToLifestyleDto(Lifestyle lifestyle)
    {
        return LifestyleDto.builder()
                .lifestyleTypeTitle(lifestyle.getLifestyleType().getTitle())
                .lifestyleBody(lifestyle.getBody())
                .build();
    }
}
