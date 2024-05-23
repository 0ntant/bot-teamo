package app.redqueen.mapper.user;

import app.redqueen.model.Dislike;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonNodeToDislikeList
{
    List<Dislike> map(JsonNode jsonNode);
}
