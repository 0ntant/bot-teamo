package app.redqueen.dto.output;

import app.redqueen.model.UserTeamo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TeamoUserDto
{
    private long id;
    private String name;
    private Integer age;
    private String city;

    public static TeamoUserDto mapToTeamoUserDto(UserTeamo userTeamo)
    {
        return TeamoUserDto.builder()
                .id(userTeamo.getId())
                .name(userTeamo.getName())
                .age(userTeamo.getAge())
                .city(userTeamo.getCity())
                .build();
    }
}

