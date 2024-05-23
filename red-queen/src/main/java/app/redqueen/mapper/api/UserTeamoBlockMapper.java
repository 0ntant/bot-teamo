package app.redqueen.mapper.api;

import app.redqueen.model.UserTeamoBlock;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

public class UserTeamoBlockMapper implements JsonNodeToUserTeamoBlock
{
    @Override
    public UserTeamoBlock map(JsonNode jsonNode)
    {
        UserTeamoBlock errorTeamo;
        if (jsonNode.path("status").asText().equals("ERROR"))
        {
            errorTeamo =  UserTeamoBlock.builder()
                    .isBlocking(true)
                    .blockDate(new Date())
                    .teamoCode(jsonNode.path("error").path("code").asInt())
                    .reason(jsonNode.path("error").path("message").asText())
                    .build();
            return errorTeamo;
        }
        return null;
    }
}
