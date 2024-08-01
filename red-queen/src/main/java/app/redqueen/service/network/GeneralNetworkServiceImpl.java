package app.redqueen.service.network;

import app.redqueen.mapper.api.JsonNodeToUserTeamoBlock;
import app.redqueen.mapper.api.UserTeamoBlockMapper;
import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@NoArgsConstructor
@Service
public abstract class GeneralNetworkServiceImpl
        implements GeneralNetworkService
{
    private JsonNodeToUserTeamoBlock errorMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Getter
    private UserTeamo clientUser;

    public GeneralNetworkServiceImpl(UserTeamo clientUser)
    {
        errorMapper = new UserTeamoBlockMapper();
        this.clientUser = clientUser;
    }

    @Override
    public Optional<UserTeamoBlock> checkResponseBlock(JsonNode responseToCheck)
    {
        UserTeamoBlock userBlock = errorMapper.map(responseToCheck);
        if (userBlock != null)
        {
            logger.warn("Error response from teamo API code={} reason={}",
                    userBlock.getTeamoCode(),
                    userBlock.getReason());

            switch (userBlock.getReason())
            {
                case "uploaded profile photo was rejected",
                     "Encounter day limit",
                     "user is self deleted":
                    userBlock.setIsBlocking(true);
                    break;
                case "person block my user":
                    userBlock.setIsBlocking(false);
                    break;
                default :
                    userBlock.setIsBlocking(true);
                    logger.error("Unchecked API response");
                    break;
            }
            return Optional.of(userBlock);
        }
        return Optional.empty();
    }
}
