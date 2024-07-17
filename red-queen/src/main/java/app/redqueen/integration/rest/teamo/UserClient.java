package app.redqueen.integration.rest.teamo;

import com.fasterxml.jackson.databind.JsonNode;

public interface UserClient
{
    JsonNode getUserInfoById(long userId);

    JsonNode getUserInfoSelf();

    JsonNode getMessagesByUser(long page, long perPage);

    JsonNode getShardableMessages(long personId, long limit, long offset);

    JsonNode sendMessage(long userId, String message);
}
