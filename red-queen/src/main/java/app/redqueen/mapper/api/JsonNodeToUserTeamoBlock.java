package app.redqueen.mapper.api;

import app.redqueen.model.UserTeamoBlock;
import com.fasterxml.jackson.databind.JsonNode;

public interface JsonNodeToUserTeamoBlock
{
    UserTeamoBlock map(JsonNode jsonNode);
}
