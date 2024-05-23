package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.UserTeamo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Builder
@Data
public class UserInBlackListDto
{
    Long id;
    String name;
    Integer height;
    String city;
    Date lastVisit;
    Date sysCreateDate;

    static UserInBlackListDto mapToUserInBlackListDto(UserTeamo userTeamo)
    {
        return UserInBlackListDto.builder()
                .id(userTeamo.getId())
                .name(userTeamo.getName())
                .height(userTeamo.getHeight())
                .city(userTeamo.getCity())
                .lastVisit(userTeamo.getLastVisit())
                .sysCreateDate(userTeamo.getSysCreateDate())
                .build();
    }
}
