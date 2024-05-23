package app.redqueen.integration.rest.url;

import org.springframework.stereotype.Component;

@Component
public class UserAPI
{
    private  String profile;
    private String messagesByUser;
    private String sendMessage;
    private String shardableMessages;

    public UserAPI(

    )
    {
        this.profile = "https://api.teamo.ru/user.profile";
        this.messagesByUser = "https://api.teamo.ru/user.messagesByUser";
        this.sendMessage = "https://api.teamo.ru/user.sendMessage";
        this.shardableMessages = "https://api.teamo.ru/user.getShardableMessages";
    }

    public String getProfile() {
        return profile;
    }

    public String getMessagesByUser() {
        return messagesByUser;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public String getShardableMessages() {
        return shardableMessages;
    }
}
