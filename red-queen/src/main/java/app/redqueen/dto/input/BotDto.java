package app.redqueen.dto.input;

import app.redqueen.model.UserTeamo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotDto
{
    private long id;
    private String password;
    private String email;
    private String token;

    public static UserTeamo BotDtoToUserTeamo(BotDto botDto)
    {
        return UserTeamo.builder()
                .id(botDto.getId())
                .password(botDto.getPassword())
                .email(botDto.getEmail())
                .token(botDto.getToken())
                .build();
    }

}
