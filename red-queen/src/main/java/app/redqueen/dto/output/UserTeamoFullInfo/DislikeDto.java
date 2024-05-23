package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.Dislike;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DislikeDto
{
    String name;
    String text;

    static DislikeDto mapToDislikeDto(Dislike dislike)
    {
        return new DislikeDto(dislike.getName(), dislike.getText());
    }
}
