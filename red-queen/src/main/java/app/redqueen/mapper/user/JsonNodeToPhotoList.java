package app.redqueen.mapper.user;

import app.redqueen.model.Photo;
import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonNodeToPhotoList
{
    List<Photo> map(JsonNode jsonNode, UserTeamo user);
}
