package img.gen.mapper;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class PixabayMapper
{
    public static List<String> map(JsonNode json)
    {
        List<String> urls = new ArrayList<>();
        for(JsonNode jsonPhoto : json.path("hits"))
        {
            urls.add(jsonPhoto.path("webformatURL").asText());
        }
        return urls;
    }
}
