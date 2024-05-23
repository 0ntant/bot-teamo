package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.UserLooking;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLookingDto
{
    String lookingTypeTitle;
    String lookingBody;

    static UserLookingDto mapToUserLookingDto(UserLooking userLooking)
    {
        return new UserLookingDto(userLooking.getLookingType().getTitle(), userLooking.getBody());
    }
}
