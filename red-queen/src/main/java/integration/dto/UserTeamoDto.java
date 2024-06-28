package integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTeamoDto
{
    Long userId;
    String token;
    String email;
    String password;
}
