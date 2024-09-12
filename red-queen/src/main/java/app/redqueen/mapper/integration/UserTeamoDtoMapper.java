package app.redqueen.mapper.integration;

import app.redqueen.model.UserTeamo;
import integration.dto.UserTeamoDto;

import java.util.Date;

public class UserTeamoDtoMapper
{
    public static UserTeamo map(UserTeamoDto userTeamoDto)
    {
        return UserTeamo.builder()
                .id(userTeamoDto.getUserId())
                .token(userTeamoDto.getToken())
                .email(userTeamoDto.getEmail())
                .password(userTeamoDto.getPassword())
                .sysCreateDate(new Date())
                .build();
    }
}
