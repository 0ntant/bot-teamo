package app.redqueen.service.network;

import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public interface GeneralNetworkService
{
    Optional<UserTeamoBlock> checkResponseBlock(JsonNode responseToCheck);

    UserTeamo getClientUser();
}
