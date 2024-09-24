package app.redqueen.service.network;

import app.redqueen.integration.teamo.*;
import app.redqueen.mapper.api.JsonNodeToSuccessLikeMessage;
import app.redqueen.mapper.api.SuccessLikeMessageMapper;
import app.redqueen.mapper.user.*;
import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class UserTeamoNetworkServiceImpl
        extends GeneralNetworkServiceImpl
        implements UserTeamoNetworkService
{

    private JsonNodeToUserTemo userMapper;

    private JsonNodeToSuccessLikeMessage jsonNodeToSuccessLikeMessage;

    @Setter
    private UserClient userClient;

    @Setter
    private TeamoClient teamoClient;

    @Setter
    private FacesClient facesClient;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserTeamoNetworkServiceImpl(UserTeamo client)
    {
        super(client);
        userMapper = UserTeamoMapper.builder()
                .jsonNodeToUserLooking(new UserLookingMapper())
                .jsonNodeToLikeList(new LikesTeamoMapper())
                .jsonNodeToDislikeList(new DislikesTeamoMapper())
                .jsonNodeToGeneralAttributeList(new GeneralAttributeMapper())

                .jsonNodeToPhotoList(new PhotoTeamoMapper())
                .jsonNodeToLifestyle(new LifestyleMapper())
                .build();
        jsonNodeToSuccessLikeMessage = new SuccessLikeMessageMapper();

        userClient  = new UserClientAPI(client.getToken());
        teamoClient = new TeamoClientAPI(client.getToken());
        facesClient = new FacesClientAPI(client.getToken());
    }


    @Override
    public ResultOrError<UserTeamo> getUserFullInfoById(long userId)
    {
        UserTeamo userTeamoFromApi;
        JsonNode apiResponse;

        try
        {
            apiResponse = userClient.getUserInfoById(userId);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return ResultOrError.errorResponse(UserTeamoBlock.builder()
                    .isBlocking(false)
                    .reason(ex.getMessage())
                    .build());
        }
        Optional<UserTeamoBlock> userTeamoBlock = checkResponseBlock(apiResponse);
        if (userTeamoBlock.isPresent()  && userTeamoBlock.get().getIsBlocking() )
        {
            logger.warn("Block response during get full info user");
            return ResultOrError.errorResponse(userTeamoBlock.get());
        };
        userTeamoFromApi = userMapper.map(apiResponse);


        //перенсти
        if (userTeamoBlock.isPresent() && userTeamoBlock.get().getReason().contains("person block my user"))
        {
            logger.warn("User id={} block bot id={}",
                    userTeamoFromApi.getId(),
                    this.getClientUser().getId()
            );

            return ResultOrError.errorResponse(userTeamoBlock.get());
        }
        return ResultOrError.successResponse(userTeamoFromApi);
    }


    @Override
    public ResultOrError<UserTeamo> getSelfFullInfo()
    {
        UserTeamo userTeamoFromApi;
        JsonNode apiResponse;
        try
        {
            apiResponse = userClient.getUserInfoSelf();
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return ResultOrError.errorResponse(UserTeamoBlock.builder()
                    .isBlocking(false)
                    .reason(ex.getMessage())
                    .build());
        }
        Optional<UserTeamoBlock> userTeamoBlock = checkResponseBlock(apiResponse);
        if (userTeamoBlock.isPresent())
        {
            logger.warn("Block response during getting self full info");
            return ResultOrError.errorResponse(userTeamoBlock.get());
        }
        userTeamoFromApi = userMapper.map(apiResponse);

        userTeamoFromApi.setPassword(getClientUser().getPassword());
        userTeamoFromApi.setToken(getClientUser().getToken());
        userTeamoFromApi.setEmail(getClientUser().getEmail());
        userTeamoFromApi.setBlock(UserTeamoBlock.builder()
                        .user(userTeamoFromApi)
                        .blockDate(new Date())
                        .isBlocking(false)
                .build());

        return ResultOrError.successResponse(userTeamoFromApi);
    }


    @Override
    public ResultOrError<List<UserTeamo>> getUsersByMessages(long page, long perPage)
    {
        List<UserTeamo> usersList ;
        JsonNode apiResponse;

        try
        {
            apiResponse = userClient.getMessagesByUser(page, perPage);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return ResultOrError.errorResponse(UserTeamoBlock.builder()
                    .isBlocking(false)
                    .reason(ex.getMessage())
                    .build());
        }

        Optional<UserTeamoBlock> userTeamoBlock = checkResponseBlock(apiResponse);
        if (userTeamoBlock.isPresent())
        {
            logger.error("Block response during getting users by chat");
            return ResultOrError.errorResponse(userTeamoBlock.get());
        }
        usersList = userMapper.mapFromListMessages(apiResponse);
        return ResultOrError.successResponse(usersList);
    }

    
    @Override
    public ResultOrError<List<UserTeamo>> getAllUsersFromGuests(int page, int perPage)
    {
        List<UserTeamo> usersList;
        JsonNode apiResponse;

        try
        {
            apiResponse = teamoClient.getGuests(page, perPage);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return ResultOrError.errorResponse(UserTeamoBlock.builder()
                    .isBlocking(false)
                    .reason(ex.getMessage())
                    .build());
        }

        Optional<UserTeamoBlock> userTeamoBlock = checkResponseBlock(apiResponse);
        if(userTeamoBlock.isPresent())
        {
            logger.warn("Block response getting all users ");
            return ResultOrError.errorResponse(userTeamoBlock.get());
        }
        usersList = userMapper.mapFromList(apiResponse);
        return ResultOrError.successResponse(usersList);
    }

    
    @Override
    public ResultOrError<String> setLikeToUserById(int userId)
    {
        String msg;
        JsonNode apiResponse;
        try
        {
            apiResponse = facesClient.saveMatchVote(userId);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return ResultOrError.errorResponse(UserTeamoBlock.builder().isBlocking(false).reason(ex.getMessage()).build());
        }
        Optional<UserTeamoBlock> userTeamoBlock = checkResponseBlock(apiResponse);
        if(userTeamoBlock.isPresent())
        {
            logger.warn("Block user during setting like");
            return ResultOrError.errorResponse(userTeamoBlock.get());
        }

        msg = jsonNodeToSuccessLikeMessage.map(apiResponse);
        return ResultOrError.successResponse(msg);
    }

    
    @Override
    public ResultOrError<List<UserTeamo>> getUsersFromMatchList(int page, int perPage)
    {
        List<UserTeamo> usersList;
        JsonNode apiResponse;

        try
        {
            apiResponse = facesClient.getMatches(page, perPage);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return ResultOrError.errorResponse(UserTeamoBlock.builder()
                    .isBlocking(false)
                    .reason(ex.getMessage())
                    .build());
        }

        Optional<UserTeamoBlock> userTeamoBlock = checkResponseBlock(apiResponse);
        if(userTeamoBlock.isPresent())
        {
            logger.warn("Block response getting all users ");
            return ResultOrError.errorResponse(userTeamoBlock.get());
        }
        usersList = userMapper.mapFromList(apiResponse);
        return ResultOrError.successResponse(usersList);
    }
}
