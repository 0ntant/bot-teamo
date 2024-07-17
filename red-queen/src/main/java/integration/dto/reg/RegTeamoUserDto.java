package integration.dto.reg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegTeamoUserDto
{
    String name;
    BirthDto birth;
    String gender;
    String email;
    String password;
    Integer resPlaceIndex;

    AgeSeek ageSeek;

    String education;
    String activity;
    String income;
    String maritalStatus;
    String children;
    String religion;
    String height;
    String smoking;
    String alcohol;
    String missNextOptStep;
}
