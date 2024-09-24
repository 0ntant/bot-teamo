package app.redqueen.service.network;

import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.database.UserTeamoService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Scope("prototype")
abstract public class UserServiceDecorator
{
    protected GeneralNetworkService generalNetworkService;
    protected UserTeamoService userTeamoService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserServiceDecorator(GeneralNetworkService generalNetworkService, UserTeamoService userTeamoRepository)
    {
        this.generalNetworkService = generalNetworkService;
        this.userTeamoService = userTeamoRepository;
    }

    public Optional<UserTeamoBlock> checkResponseBlock(JsonNode responseToCheck)
    {
        return generalNetworkService.checkResponseBlock(responseToCheck);
    }

    public UserTeamo getClientUser()
    {
        return generalNetworkService.getClientUser();
    }


    public boolean checkUserDeleteProfile(ResultOrError<?> resultOrError, UserTeamo userToSetBlock)
    {
        if(resultOrError.isErrorResponse() &&
           resultOrError.getBlock().getReason().contains("deleted user profile"))
        {
            userToSetBlock.getBlock().setReason(resultOrError.getBlock().getReason());
            userToSetBlock.getBlock().setIsBlocking(true);
            userToSetBlock.getBlock().setBlockDate(new Date());
            userToSetBlock.getBlock().setTeamoCode(resultOrError.getBlock().getTeamoCode());

            userTeamoService.save(userToSetBlock);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean checkUserAddedToBlackList(ResultOrError<?> resultOrError, UserTeamo userToSetBlock)
    {

        return resultOrError.isErrorResponse() && resultOrError.getBlock().getReason().contains("person block my user");
    }

    public boolean botBlock(ResultOrError<?> resultOrError)
    {
        if (resultOrError.isErrorResponse()
                && resultOrError.getBlock().getIsBlocking()
                && resultOrError.getBlock().getReason().contains("is self deleted") )
        {
            UserTeamo userClient = getClientUser();;

            // resultOrError.getBlock().setUser(userClient);
            if (userClient.getBlock() == null)
            {
                resultOrError.getBlock().setUser(userClient);
                userClient.setBlock(resultOrError.getBlock());
            }else
            {
                userClient.getBlock().setIsBlocking(true);
                userClient.getBlock().setBlockDate(new Date());
                userClient.getBlock().setReason(resultOrError.getBlock().getReason());
                userClient.getBlock().setTeamoCode(resultOrError.getBlock().getTeamoCode());
            }

            userTeamoService.save(userClient);
            logger.warn("Bot user id={} is blocked, saved in db",
                    userClient.getId());
            return true;
        }
        return false;
    }
}
