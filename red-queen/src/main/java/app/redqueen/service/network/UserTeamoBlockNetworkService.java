package app.redqueen.service.network;


import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;

import java.util.Optional;

public interface UserTeamoBlockNetworkService extends GeneralNetworkService
{
    Optional<UserTeamoBlock> checkBlockingUser(UserTeamo userTeamoToCheck);
}
