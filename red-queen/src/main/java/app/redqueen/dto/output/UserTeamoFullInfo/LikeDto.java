package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.Like;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LikeDto
{
    String name;
    String text;

    static LikeDto mapToLikeDto(Like like)
    {
        return new LikeDto(like.getName(), like.getText());
    }
}
