package img.gen.mapper;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class UnsplashMapper
{
    public static List<String> mapList(JsonNode json)
    {
        List<String> urls = new ArrayList<>();
        for (JsonNode jsonPhoto : json.path("results"))
        {
            urls.add(jsonPhoto.path("urls").path("regular").asText());
        }
        return urls;
    }

    public static String map(JsonNode json)
    {
        return json.path("urls").path("regular").asText();
    }
}
