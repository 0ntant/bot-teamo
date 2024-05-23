package app.redqueen.mapper.user;

import app.redqueen.model.Like;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class LikesTeamoMapper implements JsonNodeToLikeList
{
    @Override
    public List<Like> map(JsonNode jsonNode)
    {
        List<JsonNode> likes = jsonNode.path("result").path("likes").findValues("likes");
        List<Like> likeList = new ArrayList<Like>();
        if (likes.isEmpty())
        {
            return likeList;
        }
        for(JsonNode like : likes.get(0))
        {
            String likeName =   like.path("name").asText();
            String likeText = like.path("text").asText();

            likeList.add(
                    new Like(
                            like.path("class").asText(),
                            likeName,
                            likeText
                    )
            );
        }
        return likeList;
    }
}
