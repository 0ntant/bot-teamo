package app.redqueen.mapper.api;

import com.fasterxml.jackson.databind.JsonNode;

public class SuccessLikeMessageMapper implements JsonNodeToSuccessLikeMessage
{
    @Override
    public String map(JsonNode jsonNode) {

        if (jsonNode.path("status").asText().equals("OK")) {
            return "Success";
        }
        return "Failure";
    }
}
