package img.gen.dto;

import img.gen.model.ImageModel;
import img.gen.util.Base64Util;

public record ImgDataDto(String name,
                         String gender,
                         String source,
                         String imgData)
{
    public static ImgDataDto map(ImageModel imageModel)
    {
        return new ImgDataDto(
                imageModel.getName(),
                imageModel.getGender(),
                imageModel.getSource(),
                new String(Base64Util.encode(imageModel.getData()))
        );
    }
    public static ImageModel map(ImgDataDto imgDataDto)
    {
        return ImageModel.builder()
                .name(imgDataDto.name)
                .gender(imgDataDto.gender)
                .data(Base64Util.decode(imgDataDto.imgData.getBytes()))
                .source(imgDataDto.source)
                .build();
    }
}
