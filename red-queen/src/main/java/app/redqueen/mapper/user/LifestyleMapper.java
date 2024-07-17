package app.redqueen.mapper.user;

import app.redqueen.model.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LifestyleMapper implements JsonNodeToLifestyle
{
    @Override
    public List<Lifestyle> map(JsonNode jsonNode, UserTeamo userTeamo)
    {
        List<Lifestyle> lifestyleList = new ArrayList<>();

        String aboutMeBody = jsonNode
                .path("result")
                .path("profile")
                .path("status_message")
                .asText();
        if (!aboutMeBody.isEmpty())
        {
            LifestyleType lifestyleType = LifestyleType.builder()
                    .label("status_message")
                    .build();

            lifestyleList.add(
                        Lifestyle.builder()
                                .body(aboutMeBody)
                                .lifestyleType(lifestyleType)
                                .user(userTeamo)
                                .build()
                    );
        }

        for (
                Iterator<Map.Entry<String,
                JsonNode>> it = jsonNode.path("result").path("lifestyle").fields();
                it.hasNext();
             )
        {
            Map.Entry<String, JsonNode> jsonObject = it.next();
            {
                JsonNode jsonNodeValues = jsonObject.getValue();
                String lifestyleBody = jsonNodeValues.path("text").asText();

                String label = jsonObject.getKey();
                LifestyleType lifestyleType = LifestyleType.builder()
                        .label(label)
                        .build();
                if(!lifestyleBody.equals("0") && !lifestyleBody.isEmpty())
                {
                    lifestyleList.add(
                            Lifestyle.builder()
                                    .body(lifestyleBody)
                                    .user(userTeamo)
                                    .lifestyleType(lifestyleType)
                                    .build()
                    );
                }
            }
        }
        return lifestyleList;
    }
}
