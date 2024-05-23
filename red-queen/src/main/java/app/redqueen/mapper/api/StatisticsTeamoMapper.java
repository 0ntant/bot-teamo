package app.redqueen.mapper.api;

import app.redqueen.model.StatisticsTeamo;
import com.fasterxml.jackson.databind.JsonNode;

public class StatisticsTeamoMapper implements JsonNodeToStatisticsTeamo
{
    @Override
    public StatisticsTeamo map(JsonNode jsonNode)
    {
        JsonNode result = jsonNode.path("result").path("counters");
        return  StatisticsTeamo.builder()
                .dialogsAllNew(result.path("dialogs_all_new").asLong())
                .dialogsMutualNew(result.path("dialogs_mutual_new").asLong())
                .notificationsNew(result.path("notifications_new").asLong())
                .notificationsNewV2(result.path("notifications_new_v2").asLong())
                .guestsNew(result.path("guests_new").asLong())
                .facesIncomeNew(result.path("faces_income_new").asLong())
                .commentsNew(result.path("comments_new").asLong())
                .inFavouritesNew(result.path("in_favourites_new").asLong())
                .psychoSelection(result.path("psycho_selection").asLong())
                .build();
    }
}
