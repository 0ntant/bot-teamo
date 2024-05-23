package app.redqueen.mapper.user;

import app.redqueen.model.GeneralAttribute;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class GeneralAttributeMapper implements JsonNodeToGeneralAttributeList
{
    @Override
    public List<GeneralAttribute> map(JsonNode jsonNode)
    {
        List<JsonNode> generalAttributes = jsonNode.path("result").path("general_v2").findValues("fields");
        List<GeneralAttribute> generalAttributeList = new ArrayList<GeneralAttribute>();
        if(generalAttributes.isEmpty())
        {
          return generalAttributeList;
        }
        for (JsonNode generalAttribute : generalAttributes.get(0))
        {
            String attributeName =  generalAttribute.path("name").asText();
            String attributeValueText =  generalAttribute.path("value_text").asText();

            generalAttributeList.add(new GeneralAttribute
                    (
                            attributeName,
                            //  generalAttribute.path("value").asInt(),
                            attributeValueText
                    ));
        }
        return generalAttributeList;
    }
}
