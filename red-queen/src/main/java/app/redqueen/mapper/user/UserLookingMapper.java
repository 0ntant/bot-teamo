package app.redqueen.mapper.user;

import app.redqueen.model.LookingType;
import app.redqueen.model.UserLooking;
import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserLookingMapper implements JsonNodeToUserLooking

{
    public List<UserLooking> map(JsonNode jsonNode, UserTeamo userTeamo)
    {
        List<UserLooking> userLookingList = new ArrayList<>();
        for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.path("result").path("looking_for").fields(); it.hasNext(); )
        {
            Map.Entry<String, JsonNode> jsonObject = it.next();
            {
                 String lookingBody = jsonObject.getValue().asText();
                String lookingLabel = jsonObject.getKey();
                LookingType lookingType = LookingType.builder()
                        .label(lookingLabel)
                        .build();
                if(!lookingBody.equals("0") && !lookingBody.isEmpty())
                {
                    userLookingList.add(
                            UserLooking.builder()
                                    .body(lookingBody)
                                    .user(userTeamo)
                                    .lookingType(lookingType)
                                    .build()
                    );
                }
            }
        }
        return userLookingList;
    }
}

