package app.redqueen.service.network;

import app.redqueen.model.UserTeamo;
import app.redqueen.repository.UserTeamoRepository;
import app.redqueen.service.database.UserServiceDataFacade;
import app.redqueen.service.database.UserTeamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserNetServiceFactory
{
    @Autowired
    UserServiceDataFacade userServiceDataFacade;

    @Autowired
    UserTeamoService userTeamoRepository;

    public UserTeamoNetDbServiceDecorator createUserNetDecorator(UserTeamo client)
    {
        UserTeamoNetworkService userTeamoNetworkService = new UserTeamoNetworkServiceImpl(client);

        return new UserTeamoNetDbServiceDecorator
                (
                    userTeamoNetworkService,
                    userServiceDataFacade,
                    userTeamoRepository
                );
    }

    public UserTeamoNetDbServiceDecorator createUserNetDecorator(UserTeamoNetworkService userTeamoNetworkService)
    {
        return new UserTeamoNetDbServiceDecorator
                (
                    userTeamoNetworkService,
                    userServiceDataFacade,
                    userTeamoRepository
                );
    }
}
