package app.redqueen.mapper.user;

import app.redqueen.model.Dislike;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class DislikesTeamoMapper implements JsonNodeToDislikeList
{
    @Override
    public List<Dislike> map(JsonNode jsonNode)
    {
        List<JsonNode> dislikes = jsonNode.path("result").path("dislikes").findValues("dislikes");
        List<Dislike> dislikeList = new ArrayList<Dislike>();
        if(dislikes.isEmpty())
        {
            return dislikeList;
        }
        for(JsonNode dislike : dislikes.get(0))
        {
            String dislikeName =  dislike.path("name").asText();
            String dislikeText =  dislike.path("text").asText();

            dislikeList.add(
                    new Dislike(
                            dislike.path("class").asText(),
                            dislikeName,
                            dislikeText
                    )
            );
        }
        return dislikeList;
    }
}
