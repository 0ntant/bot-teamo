package app.redqueen.service.network;

import app.redqueen.model.UserTeamo;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.database.UserServiceDataFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("DbDec")
@Scope("prototype")
public class UserTeamoNetDbServiceDecorator
        extends UserServiceDecorator
        implements UserTeamoNetworkService
{
    private UserTeamoNetworkService userTeamoNetService;
    private UserServiceDataFacade userServiceFacade;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public  UserTeamoNetDbServiceDecorator(
                    UserTeamoNetworkService userTeamoNetworkService,
                    UserServiceDataFacade userServiceFacade,
                    UserTeamoRepository userTeamoRepository)
    {
        super(userTeamoNetworkService, userTeamoRepository);
        this.userTeamoNetService = userTeamoNetworkService;
        this.userServiceFacade = userServiceFacade;
    }


    //user full info saving
    private void synchronizeDbSave(UserTeamo userTeamo)
    {
        logger.info("Save user id={} in database", userTeamo.getId());
        userTeamoService.save(
                userServiceFacade.synchronizeDbSave(userTeamo)
        );
    }

    @Override
    public ResultOrError<UserTeamo> getUserFullInfoById(long userId)
    {
        Optional<UserTeamo> isUserExit = userTeamoService.findById(userId);
        if (isUserExit.isPresent())
        {
            return ResultOrError.successResponse(isUserExit.get());
        }
        logger.info("User id={} do not in database getting info from API", userId);

        ResultOrError<UserTeamo> resultOrError = userTeamoNetService.getUserFullInfoById(userId);
        if(botBlock(resultOrError))
        {
            isUserExit = userTeamoService.findById(userId);
            if (resultOrError.getBlock().getReason().contains("person block my user") && isUserExit.isPresent())
            {
              //  userTeamoService.addingBotToBlackList()
                userServiceFacade.addingBotToBlackList(getClientUser(), isUserExit.get());
            }
            logger.warn("User bot is blocked can't get full info");
            return resultOrError;
        }

        synchronizeDbSave(resultOrError.getResult());
        return resultOrError;
    }



    @Override
    public ResultOrError<UserTeamo> getSelfFullInfo()
    {
        ResultOrError<UserTeamo> resultOrError = userTeamoNetService.getSelfFullInfo();
        if(botBlock(resultOrError))
        {
            logger.warn("User bot is blocked can't get full info");
            return resultOrError;
        }
        synchronizeDbSave(resultOrError.getResult());
        return resultOrError;
    }

    @Override
    public ResultOrError<String> setLikeToUserById(int userId)
    {
        ResultOrError<String> resultOrError = userTeamoNetService.setLikeToUserById(userId);
        if (botBlock(resultOrError))
        {
            logger.warn("User bot is blocked can't get send like to user");
            return resultOrError;
        }
        return resultOrError;
    }

    @Override
    public ResultOrError<List<UserTeamo>> getUsersByMessages(long page, long perPage)
    {
        ResultOrError<List<UserTeamo>> resultOrError
                = userTeamoNetService.getUsersByMessages(page, perPage);
        if(botBlock(resultOrError))
        {
            logger.warn("User bot is blocked can't get full info");
            return resultOrError;
        }

        return getFullInfoFromUserList(resultOrError.getResult());
    }

    private ResultOrError<List<UserTeamo>> getFullInfoFromUserList(List<UserTeamo> userTeamoListWithPartInfo)
    {
        for (int i = 0; i < userTeamoListWithPartInfo.size() ; i++)
        {
            ResultOrError<UserTeamo> resultOrErrorFullInfo = getUserFullInfoById(userTeamoListWithPartInfo.get(i).getId());
            if (resultOrErrorFullInfo.isErrorResponse())
            {
                return ResultOrError.errorResponse(resultOrErrorFullInfo.getBlock());
            }
            userTeamoListWithPartInfo.set(i, resultOrErrorFullInfo.getResult());
        }
        return ResultOrError.successResponse(userTeamoListWithPartInfo);
    }

    @Override
    public ResultOrError<List<UserTeamo>> getAllUsersFromGuests(int page, int perPage)
    {
        ResultOrError<List<UserTeamo>> resultOrError
                = userTeamoNetService.getAllUsersFromGuests(page, perPage);
        if(botBlock(resultOrError))
        {
            logger.warn("User bot is blocked can't get users from guests");
            return resultOrError;
        }

        return getFullInfoFromUserList(resultOrError.getResult());
    }

    @Override
    public ResultOrError<List<UserTeamo>> getUsersFromMatchList(int page, int perPage) {
        ResultOrError<List<UserTeamo>> resultOrError
                = userTeamoNetService.getUsersFromMatchList(page, perPage);
        if(botBlock(resultOrError))
        {
            logger.warn("User bot is blocked can't get users from matches");
            return resultOrError;
        }

        return getFullInfoFromUserList(resultOrError.getResult());
    }
}
