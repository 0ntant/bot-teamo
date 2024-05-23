package app.redqueen.mapper.user;

import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonNodeToMessageTeamo
{
    List<MessageTeamo> mapFromShardableMessagesMethod(JsonNode jsonNode, UserTeamo userSender, UserTeamo userReceiver);
    MessageTeamo map(JsonNode jsonNode, UserTeamo userSender, UserTeamo userReceiver);
}
