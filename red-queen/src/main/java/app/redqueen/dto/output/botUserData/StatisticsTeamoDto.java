package app.redqueen.dto.output.botUserData;

import app.redqueen.model.StatisticsTeamo;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Builder
public class StatisticsTeamoDto
{
     Long guestsNew;
     Long dialogsMutualNew;
     Long commentsNew;

     static StatisticsTeamoDto mapToStatisticsTeamoDto(StatisticsTeamo statisticsTeamo)
     {
         return StatisticsTeamoDto.builder()
                 .guestsNew(statisticsTeamo.getGuestsNew())
                 .dialogsMutualNew(statisticsTeamo.getDialogsMutualNew())
                 .commentsNew(statisticsTeamo.getCommentsNew())
                 .build();
     }
}
