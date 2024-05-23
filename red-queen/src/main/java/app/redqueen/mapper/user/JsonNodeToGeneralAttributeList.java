package app.redqueen.mapper.user;

import app.redqueen.model.GeneralAttribute;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonNodeToGeneralAttributeList
{
    List<GeneralAttribute> map(JsonNode jsonNode);
}
