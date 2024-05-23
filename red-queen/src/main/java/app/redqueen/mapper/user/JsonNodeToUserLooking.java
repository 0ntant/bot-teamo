package app.redqueen.mapper.user;

import app.redqueen.model.UserLooking;
import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonNodeToUserLooking
{
    List<UserLooking> map (JsonNode jsonNode, UserTeamo userTeamo);
}
