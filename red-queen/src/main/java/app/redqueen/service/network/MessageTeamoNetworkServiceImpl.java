package app.redqueen.service.network;

import app.redqueen.integration.rest.teamo.UserClient;
import app.redqueen.integration.rest.teamo.UserClientAPI;
import app.redqueen.mapper.user.JsonNodeToMessageTeamo;
import app.redqueen.mapper.user.MessageTeamoMapper;

import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageTeamoNetworkServiceImpl extends GeneralNetworkServiceImpl implements MessageTeamoNetworkService {
    private JsonNodeToMessageTeamo messageMapper;

    @Setter
    private UserClient userClient;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageTeamoNetworkServiceImpl(UserTeamo userTeamo)
    {
        super(userTeamo);
        messageMapper = new MessageTeamoMapper();
        userClient = new UserClientAPI(userTeamo.getToken());
    }

    @Override
    public ResultOrError<List<MessageTeamo>> getShareableMessages(UserTeamo owner, UserTeamo partner, int limit, int offset)
    {
        List<MessageTeamo> messageTeamoList;
        JsonNode apiResponse;
        try
        {
            apiResponse = userClient.getShardableMessages(partner.getId(), limit, offset);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            return ResultOrError.errorResponse(UserTeamoBlock.builder()
                    .isBlocking(false)
                    .reason(ex.getMessage())
                    .build());
        }

        Optional<UserTeamoBlock> userBlock = checkResponseBlock(apiResponse);
        if (userBlock.isPresent())
        {
            logger.warn("Error response to get Shareable messages");
            return ResultOrError.errorResponse(userBlock.get());
        }
        messageTeamoList = messageMapper.mapFromShardableMessagesMethod(apiResponse, owner, partner);
        return ResultOrError.successResponse(messageTeamoList);
    }

    @Override
    public ResultOrError<MessageTeamo> sendMessage(UserTeamo sender, UserTeamo receiver, String message)
    {
        MessageTeamo messageTeamo;
        JsonNode apiResponse;
        try
        {
            apiResponse = userClient.sendMessage(receiver.getId(), message);
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
            logger.warn("Blocked response to send message");
            return ResultOrError.errorResponse(userTeamoBlock.get());
        }
        messageTeamo = messageMapper.map(apiResponse, sender, receiver);
        return ResultOrError.successResponse(messageTeamo);
    }
}
