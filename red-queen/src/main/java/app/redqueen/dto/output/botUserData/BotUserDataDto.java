package app.redqueen.dto.output.botUserData;

import app.redqueen.model.BotUserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BotUserDataDto
{
    long userId;
    String name;
    String city;
    String state;
    StatisticsTeamoDto statisticsTeamo;

    public static BotUserDataDto mapToBotUserDataDto(BotUserData botUserData)
    {
        return BotUserDataDto.builder()
                .userId(botUserData.getUserId())
                .name(botUserData.getName())
                .city(botUserData.getCity())
                .state(botUserData.getState())
                .statisticsTeamo(StatisticsTeamoDto.mapToStatisticsTeamoDto(
                        botUserData.getStatisticsTeamo())
                )
                .build();
    }
}
