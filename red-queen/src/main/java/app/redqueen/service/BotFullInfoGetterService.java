package app.redqueen.service;

import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.ResultOrError;
import app.redqueen.service.network.UserNetServiceFactory;
import app.redqueen.service.network.UserTeamoNetworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BotFullInfoGetterService
{
    @Autowired
    private UserTeamoService userTeamoService;

    @Autowired
    private UserNetServiceFactory userNetServiceFactory;

    private List<UserTeamo> usersToGetInfo = new ArrayList<>();

    public void getFullInfoUser(UserTeamo userTeamo)
    {
        if(userTeamoService.isExistById(userTeamo.getId()))
        {
            log.warn("User id={} already exist", userTeamo.getId());
            return;
        }

        try
        {
            tryToRegUser(userTeamo);
        }
        catch (Exception ex)
        {
            log.warn(ex.getMessage());
            log.info("Add user to get full info to queue id={}",userTeamo.getId());
            usersToGetInfo.add(userTeamo);
        }
    }

    public void getRequestUsersFromList()
    {
        List<UserTeamo> usersNotRequested = new ArrayList<>();
        if (!usersToGetInfo.isEmpty())
        {
            for(UserTeamo userTeamoToReg : usersToGetInfo)
            {
                try
                {
                    tryToRegUser(userTeamoToReg);
                }
                catch (Exception ex)
                {
                    usersNotRequested.add(userTeamoToReg);
                    log.warn(ex.getMessage());
                }
            }
            usersToGetInfo = usersNotRequested;
        }
    }

    private void tryToRegUser(UserTeamo userTeamoToReg)
    {
        UserTeamoNetworkService userTeamoNetworkService
                = userNetServiceFactory.createUserNetDecorator(userTeamoToReg);
        ResultOrError<UserTeamo> resultOrError = userTeamoNetworkService.getSelfFullInfo();
        userTeamoService.setUserCreateSource(userTeamoToReg, userTeamoToReg.getCreateSource());
        if (resultOrError.isErrorResponse())
        {
            log.error("Error self info code={} reason={}",
                    resultOrError.getBlock().getTeamoCode(),
                    resultOrError.getBlock().getReason());
        }
    }
}
