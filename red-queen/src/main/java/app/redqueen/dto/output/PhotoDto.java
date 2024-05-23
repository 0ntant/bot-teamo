package app.redqueen.dto.output;

import app.redqueen.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PhotoDto
{
    String url;
    long userId;

    public static PhotoDto mapToPhotoDto(Photo photo)
    {
        return new PhotoDto(photo.getTeamoUrl(), photo.getUser().getId());
    }
}