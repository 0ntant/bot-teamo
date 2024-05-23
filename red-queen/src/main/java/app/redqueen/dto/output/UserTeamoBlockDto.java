package app.redqueen.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTeamoBlockDto
{
    long blacklistOwnerId;
    long userInBlacklistId;
}
