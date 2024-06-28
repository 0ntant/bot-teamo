package app.redqueen.dto.input;

import app.redqueen.model.UserTeamo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTeamoOrderDto
{
    String name;
    String gender;
    Integer age;
    String city;
    Integer height;

    public static UserTeamo map(UserTeamoOrderDto userTeamoOrderDto)
    {
        return UserTeamo.builder()
                .name(userTeamoOrderDto.getName())
                .gender(userTeamoOrderDto.getGender())
                .age(userTeamoOrderDto.getAge())
                .city(userTeamoOrderDto.getCity())
                .height(userTeamoOrderDto.getHeight())
                .build();
    }
}
