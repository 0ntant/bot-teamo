package auto.reg.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import integration.dto.ImgAvaDto;

public class ImgAvaDtoMapper
{
    public static ImgAvaDto result(JsonNode jsonResponse)
    {
        return new ImgAvaDto(
                jsonResponse.path("name").asText(),
                jsonResponse.path("gender").asText(),
                jsonResponse.path("imgData").asText()
        );
    }
}
