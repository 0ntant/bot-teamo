package app.redqueen.dto.output;

import app.redqueen.model.MessageTeamo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatDto
{
    long senderId;
    String senderName;
    long receiverId;
    String receiverName;

    public static ChatDto mapTochatDto(MessageTeamo messageTeamo)
    {
        return ChatDto.builder()
                .senderId(messageTeamo.getUserSender().getId())
                .senderName(messageTeamo.getUserSender().getName())
                .receiverId(messageTeamo.getUserReceiver().getId())
                .receiverName(messageTeamo.getUserReceiver().getName())
                .build();
    }
}
