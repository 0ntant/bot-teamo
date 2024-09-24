package integration.dto.reg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegTeamoUserImgDto
{
    RegTeamoUserDto userDto;
    ImageAvaDto imageDto;
    String createSource;
}
