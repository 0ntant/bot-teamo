package app.redqueen.mapper.api;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonNodeToSuccessLikeMessage
{
    String map(JsonNode jsonNode);
}
