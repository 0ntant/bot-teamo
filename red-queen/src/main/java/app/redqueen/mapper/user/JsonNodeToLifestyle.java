package app.redqueen.mapper.user;

import app.redqueen.model.Lifestyle;
import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonNodeToLifestyle
{
    List<Lifestyle> map (JsonNode jsonNode, UserTeamo userTeamo);
}
