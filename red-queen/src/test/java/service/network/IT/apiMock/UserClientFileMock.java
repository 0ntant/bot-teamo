package service.network.IT.apiMock;

import app.redqueen.integration.rest.UserClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.util.NoSuchElementException;

public class UserClientFileMock extends GeneralNetMock
        implements UserClient
{
    @Override
    public JsonNode getUserInfoById(long userId)
    {
        File[] files = getUserFiles();

        for (File userFile : files)
        {

            if (userFile.getName().equalsIgnoreCase(String.valueOf(userId)))
            {
                try
                {
                    return objectMapper.readTree(userFile.getAbsoluteFile());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                   return null;
                }
            }

        }
        try
        {
            return super.getBlockResponse(String.valueOf(userId));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JsonNode getUserInfoSelf()
    {
        File profile = new File(selfFolder.getAbsolutePath().concat("/profile"));

        try
        {
            return objectMapper.readTree(profile.getAbsoluteFile());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        throw new NoSuchElementException(String.format("No such mock file %s self info in directory %s",
                profile.getName(), selfFolder.getAbsolutePath()
        ));
    }

    @Override
    public JsonNode getMessagesByUser(long page, long perPage)
    {
        File messagesByUser = new File(messagesFolder.getAbsolutePath().concat("/messagesByUser"));
        try
        {
            return objectMapper.readTree(messagesByUser.getAbsoluteFile());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        throw new NoSuchElementException(String.format("No such mock message file %s self info in directory %s",
                messagesByUser.getName(), messagesFolder.getAbsolutePath()
        ));
    }

    @Override
    public JsonNode getShardableMessages(long personId, long limit, long offset)
    {
        File shardableMessages21596288 = new File(messagesFolder.getAbsolutePath().concat("/ShardableMessages21596288"));
        if (personId != 21596288)
        {
            throw new NoSuchElementException("No such person id");
        }
        try
        {
            return objectMapper.readTree(shardableMessages21596288.getAbsoluteFile());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        throw new NoSuchElementException(String.format("No such mock shareble message file %s self info in directory %s",
                shardableMessages21596288.getName(), messagesFolder.getAbsolutePath()
        ));
    }

    @Override
    public JsonNode sendMessage(long userId, String message)
    {
        File sendMessage = new File(messagesFolder.getAbsolutePath().concat("/sendMessage"));
        if (message.equals("blockMe"))
        {
            try {
                return super.getBlockResponse(message);
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        if (userId != 21596288)
        {
            throw new NoSuchElementException("No such person id");
        }
        try
        {
            JsonNode messageFromFile =  objectMapper.readTree(sendMessage.getAbsoluteFile());
            ((ObjectNode) messageFromFile.get("result").get("message")).put("message_text", message);
            System.out.print(messageFromFile.asText());

            return messageFromFile;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        throw new NoSuchElementException(String.format("No such mock sending message file %s self info in directory %s",
                sendMessage.getName(), messagesFolder.getAbsolutePath()
        ));
    }
}
