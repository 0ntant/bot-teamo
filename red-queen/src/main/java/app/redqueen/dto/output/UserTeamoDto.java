package app.redqueen.dto.output;

import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserTeamoDto
{
    private long id;
    private String name;
    private Integer age;
    private String city;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime sysCreateDate;

    public static UserTeamoDto mapToTeamoUserDto(UserTeamo userTeamo)
    {
        return UserTeamoDto.builder()
                .id(userTeamo.getId())
                .name(userTeamo.getName())
                .age(userTeamo.getAge())
                .city(userTeamo.getCity())
                .sysCreateDate(
                        userTeamo.
                                getSysCreateDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                )
                .build();
    }
}
