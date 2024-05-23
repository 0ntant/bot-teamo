package app.redqueen.mapper.user;

import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonNodeToUserTemo
{
    UserTeamo map(JsonNode jsonNode);

    List<UserTeamo> mapFromList(JsonNode jsonNode);

    //   UserTeamo map(JsonNode jsonNode, UserTeamo partner);

    List<UserTeamo> mapFromListMessages(JsonNode jsonNode);
}
