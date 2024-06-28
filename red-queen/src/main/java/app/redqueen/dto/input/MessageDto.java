package app.redqueen.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto
{
    private Long userWithTokenId;
    private Long userReceiverId;
    private String message;
}
