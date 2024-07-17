package app.redqueen.mapper.api;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class JsonNodeToCityIndex
{
    public static List<Integer> map(JsonNode json)
    {
        List<Integer> indexes = new ArrayList<>();
        List<JsonNode> cityIndexesJson = json.findValues("result");

        for (JsonNode cityIndexJson : cityIndexesJson.get(0))
        {
            indexes.add(cityIndexJson.path("id").asInt());
        }

        return indexes;
    }
}
