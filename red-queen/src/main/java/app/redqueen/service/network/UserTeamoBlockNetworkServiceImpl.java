package app.redqueen.service.network;

import app.redqueen.integration.teamo.UserClient;
import app.redqueen.integration.teamo.UserClientAPI;
import app.redqueen.mapper.api.JsonNodeToUserTeamoBlock;
import app.redqueen.mapper.api.UserTeamoBlockMapper;
import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class UserTeamoBlockNetworkServiceImpl extends GeneralNetworkServiceImpl implements UserTeamoBlockNetworkService
{
    private JsonNodeToUserTeamoBlock userBlockMapper;
    private UserClient userClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserTeamoBlockNetworkServiceImpl(UserTeamo client)
    {
        super();
        userBlockMapper = new UserTeamoBlockMapper();
        userClient = new UserClientAPI(client.getToken());
    }

    @Override
    public Optional<UserTeamoBlock> checkBlockingUser(UserTeamo userTeamoToCheck)
    {
        JsonNode apiResponse;
        Optional<UserTeamoBlock> userTeamoBlock;
        try
        {
            apiResponse = userClient.getUserInfoById(userTeamoToCheck.getId());
        }
        catch (Exception ex)
        {
            logger.error("Error during checking block ");
            ex.printStackTrace();
            return Optional.empty();
        }

        userTeamoBlock = checkResponseBlock(apiResponse);
        if (userTeamoBlock.isEmpty())
        {
            logger.warn("User id={} not blocked", userTeamoToCheck.getId());
            return Optional.empty();
        }

        userTeamoBlock.get().setUser(userTeamoToCheck);
        return userTeamoBlock;
    }
}
