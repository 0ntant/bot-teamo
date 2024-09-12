package img.gen.mapper;

import com.fasterxml.jackson.databind.JsonNode;

public class PexelsMapper
{
    public static String map(JsonNode jsonNode)
    {
        return jsonNode.path("photos").get(0).path("src").path("large2x").asText();
    }
}
