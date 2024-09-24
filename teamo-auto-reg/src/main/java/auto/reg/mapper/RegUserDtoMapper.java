package auto.reg.mapper;

import integration.dto.reg.RegTeamoUserDto;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RegUserDtoMapper
{
    public static MultiValueMap<String, String> getParams(RegTeamoUserDto regTeamoUserDto)
    {
        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();

        params.add("confirm[name]", regTeamoUserDto.getName());

        params.add("confirm[birthdate][day]", String.valueOf(regTeamoUserDto.getBirth().getDay()));
        params.add("confirm[birthdate][month]", String.valueOf(regTeamoUserDto.getBirth().getMonthNumber()));
        params.add("confirm[birthdate][year]", String.valueOf(regTeamoUserDto.getBirth().getYear()));

        params.add("confirm[location_id]", String.valueOf(regTeamoUserDto.getResPlaceIndex()));
        params.add("user_info[education]", regTeamoUserDto.getEducation());
        params.add("user_info[field_of_activity]", regTeamoUserDto.getEducation());
        params.add("user_info[income]", regTeamoUserDto.getIncome());
        params.add("user_info[marital_status]", regTeamoUserDto.getMaritalStatus());
        params.add("user_info[children]", regTeamoUserDto.getChildren());
        params.add("user_info[religion]", regTeamoUserDto.getReligion());
        params.add("user_info[height]", regTeamoUserDto.getHeight());
        params.add("user_info[smoking]", regTeamoUserDto.getSmoking());
        params.add("user_info[alcohol]", regTeamoUserDto.getAlcohol());

        params.add("looking_for_short[user_looking_for_from]", String.valueOf(regTeamoUserDto.getAgeSeek().getAgeFrom()));
        params.add("looking_for_short[user_looking_for_to]", String.valueOf(regTeamoUserDto.getAgeSeek().getAgeTo()));

        params.add("miss_next_opt_step", regTeamoUserDto.getMissNextOptStep());

        return params;
    }

    public static MultiValueMap<String, String> getParamsV2(RegTeamoUserDto regTeamoUserDto)
    {
        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();

        params.add("confirm[name]", regTeamoUserDto.getName());

        params.add("confirm[birthdate][day]", String.valueOf(regTeamoUserDto.getBirth().getDay()));
        params.add("confirm[birthdate][month]", String.valueOf(regTeamoUserDto.getBirth().getMonthNumber()));
        params.add("confirm[birthdate][year]", String.valueOf(regTeamoUserDto.getBirth().getYear()));

        params.add("confirm[location_id]", String.valueOf(regTeamoUserDto.getResPlaceIndex()));
        params.add("confirm[sex]", regTeamoUserDto.getGender());
        params.add("user_info[education]", regTeamoUserDto.getEducation());
        params.add("user_info[field_of_activity]", regTeamoUserDto.getEducation());
        params.add("user_info[income]", regTeamoUserDto.getIncome());
        params.add("user_info[marital_status]", regTeamoUserDto.getMaritalStatus());
        params.add("user_info[children]", regTeamoUserDto.getChildren());
        params.add("user_info[religion]", regTeamoUserDto.getReligion());
        params.add("user_info[height]", regTeamoUserDto.getHeight());
        params.add("user_info[smoking]", regTeamoUserDto.getSmoking());
        params.add("user_info[alcohol]", regTeamoUserDto.getAlcohol());

        params.add("looking_for_short[user_looking_for_from]", String.valueOf(regTeamoUserDto.getAgeSeek().getAgeFrom()));
        params.add("looking_for_short[user_looking_for_to]", String.valueOf(regTeamoUserDto.getAgeSeek().getAgeTo()));

        params.add("use_gender_fields", "1");
        params.add("miss_next_opt_step", regTeamoUserDto.getMissNextOptStep());

        return params;
    }
}
