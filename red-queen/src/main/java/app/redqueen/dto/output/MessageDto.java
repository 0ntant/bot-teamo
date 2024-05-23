package app.redqueen.dto.output;

import app.redqueen.model.MessageTeamo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageDto
{
    private long id;
    private String senderName;
    private long senderId;
    private String receiverName;
    long receiverId;
    private String body;
    private Date date;

    public static MessageDto mapToMessageDto(MessageTeamo messageTeamo)
    {
        return MessageDto.builder()
                .id(messageTeamo.getId())
                .senderName(messageTeamo.getUserSender().getName())
                .senderId(messageTeamo.getUserSender().getId())
                .receiverName(messageTeamo.getUserReceiver().getName())
                .receiverId(messageTeamo.getUserReceiver().getId())
                .body(messageTeamo.getBody())
                .date(messageTeamo.getCreateDate())
                .build();
    }
}
