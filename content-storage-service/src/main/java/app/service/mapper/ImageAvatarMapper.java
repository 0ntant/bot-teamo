package app.service.mapper;

import app.service.model.ImageAvatar;
import integration.dto.ImgAvaDto.ImgAvaDto;

import java.util.Date;

public class ImageAvatarMapper
{
    public static ImageAvatar map(ImgAvaDto imgAvaDto, String fullPath)
    {
        ImageAvatar imageAvatar = new ImageAvatar();
        imageAvatar.setGender(imgAvaDto.getGender());
        imageAvatar.setPath(fullPath);
        imageAvatar.setSysCreateDate(new Date());

        return imageAvatar;
    }
}
