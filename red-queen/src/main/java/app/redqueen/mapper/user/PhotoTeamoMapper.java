package app.redqueen.mapper.user;

import app.redqueen.model.Photo;
import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class PhotoTeamoMapper implements JsonNodeToPhotoList
{
    @Override
    public List<Photo> map(JsonNode jsonNode , UserTeamo user)
    {
        List<JsonNode> photos = jsonNode.path("result").path("photos_v2").findValues("photos");
        List<Photo> photoList = new  ArrayList<Photo>();
        if (photos.isEmpty())
        {
            return photoList;
        }
        for (JsonNode photo : photos.get(0))
        {
            String photoId = photo.path("id").asText();
            photoList.add(new Photo
                    (
                       Long.parseLong(photoId),
                       photo.path("url").path("gallery").asText(),
                       user
                    ));
        }
        return photoList;
    }
}
