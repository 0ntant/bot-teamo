package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.GeneralAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GeneralAttributeDto
{
    String name;
    String valueText;

    static GeneralAttributeDto mapToGeneralAttributeDto(GeneralAttribute generalAttribute)
    {
        return new GeneralAttributeDto(generalAttribute.getName(), generalAttribute.getValueText());
    }
}
