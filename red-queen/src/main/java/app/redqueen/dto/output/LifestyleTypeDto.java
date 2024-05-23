package app.redqueen.dto.output;


import app.redqueen.model.LifestyleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LifestyleTypeDto
{
    String label;
    String title;

    public static LifestyleTypeDto mapToLifestyleTypeDto(LifestyleType lifestyleType)
    {
        return new LifestyleTypeDto(lifestyleType.getLabel(), lifestyleType.getTitle());
    }
}
