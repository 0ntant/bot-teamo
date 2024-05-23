package app.redqueen.mapper.user;

import app.redqueen.model.Like;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonNodeToLikeList
{
    List<Like> map(JsonNode jsonNode);
}
