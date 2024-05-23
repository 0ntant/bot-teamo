package app.redqueen.service.network;

import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;

import java.util.List;

public interface MessageTeamoNetworkService
        extends GeneralNetworkService
{
    ResultOrError<List<MessageTeamo>> getShareableMessages(UserTeamo owner, UserTeamo partner, int limit, int offset);

    ResultOrError<MessageTeamo> sendMessage(UserTeamo sender, UserTeamo receiver, String message);
}
