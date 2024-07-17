package app.redqueen.mapper.user;

import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessageTeamoMapper implements JsonNodeToMessageTeamo
{
    @Override
    public List<MessageTeamo> mapFromShardableMessagesMethod(
            JsonNode jsonNode, UserTeamo userSender, UserTeamo userReceiver)
    {
        List<JsonNode> messages = jsonNode.path("result").findValues("messages");
        List<MessageTeamo> teamoMessages = new ArrayList<>();
        if (messages.isEmpty())
        {
            return teamoMessages;
        }
        for (JsonNode message : messages.get(0))
        {
            teamoMessages.add(mapToMessage(message, userSender, userReceiver));
        }
        return teamoMessages;
    }

    @Override
    public MessageTeamo map(JsonNode jsonNode, UserTeamo userSender, UserTeamo userReceiver) {
        return mapToMessage(jsonNode.path("result").path("message"), userSender, userReceiver);
    }

    public MessageTeamo mapToMessage(JsonNode jsonNode, UserTeamo userSender, UserTeamo userReceiver)
    {
        Date dateToMessageTeamo = new Date(TimeUnit.SECONDS.toMillis(jsonNode.path("date").asLong()));
        MessageTeamo mgsToMap = new MessageTeamo(
                jsonNode.path("message_text").asText(),
                dateToMessageTeamo
        );
        if (userSender.getId() == jsonNode.path("sender_id").asLong())
        {
            mgsToMap.setUserSender(userSender);
            mgsToMap.setUserReceiver(userReceiver);
        }
        else
        {
            mgsToMap.setUserSender(userReceiver);
            mgsToMap.setUserReceiver(userSender);
        }
        return mgsToMap;
    }
}
