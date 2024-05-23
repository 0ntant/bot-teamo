package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PhotoDto
{
    String url;

    static PhotoDto mapToPhotoDto(Photo photo)
    {
        return new PhotoDto(photo.getTeamoUrl());
    }
}
