package app.redqueen.mapper.api;

import app.redqueen.model.StatisticsTeamo;
import com.fasterxml.jackson.databind.JsonNode;

public interface JsonNodeToStatisticsTeamo
{
    StatisticsTeamo map(JsonNode jsonNode);
}
