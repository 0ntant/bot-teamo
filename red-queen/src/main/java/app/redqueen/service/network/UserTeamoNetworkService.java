package app.redqueen.service.network;


import app.redqueen.model.UserTeamo;

import java.util.List;

public interface UserTeamoNetworkService extends GeneralNetworkService
{
    ResultOrError<UserTeamo> getUserFullInfoById(long userId);

    ResultOrError<UserTeamo> getSelfFullInfo();

    ResultOrError<List<UserTeamo>> getUsersByMessages(long page, long perPage);

    ResultOrError<List<UserTeamo>> getAllUsersFromGuests(int page, int perPage);

    ResultOrError<String> setLikeToUserById(int userId);

    ResultOrError<List<UserTeamo>> getUsersFromMatchList(int page, int perPage);
}
