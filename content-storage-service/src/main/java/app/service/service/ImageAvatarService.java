package app.service.service;

import app.service.model.ImageAvatar;
import app.service.model.ObjectSum;
import app.service.repository.ImageAvatarRepository;
import app.service.util.Base64Util;
import app.service.util.FileUtil;
import app.service.util.MD5Util;
import integration.dto.ImgAvaDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class ImageAvatarService
{
    @Autowired
    ImageAvatarRepository imageRep;

    @Getter
    @Value("${file.images.avatar.dir}")
    String dir;

    public void saveFromImgGenerator(ImgAvaDto imgAvaDto)
    {
        save(
                mapToImageAvatar(imgAvaDto),
                Base64Util.getBytesFromBase64(imgAvaDto.getBase64().getBytes())
        );
    }

    public ImageAvatar mapToImageAvatar(ImgAvaDto imgAvaDto)
    {
        ImageAvatar imageAvatar = new ImageAvatar();
        imageAvatar.setGender(imgAvaDto.getGender());
        imageAvatar.setPath(getFullImgPath(imgAvaDto.getName()));
        imageAvatar.setSysCreateDate(new Date());

        ObjectSum objectSum = new ObjectSum();
        objectSum.setObjectHash(MD5Util.getHashFromBytes(
                Base64Util.getBytesFromBase64(imgAvaDto.getBase64().getBytes())));
        imageAvatar.setObjectSum(objectSum);
        return imageAvatar;
    }

    public void save(ImageAvatar imageAvatar, byte[] imgBytes)
    {
        imageRep.save(imageAvatar);
        FileUtil.save(imageAvatar.getPath(), imgBytes);
    }

    public long count()
    {
        return imageRep.count();
    }

    public ImageAvatar getRandByGender(String gender)
    {
        ImageAvatar imageAvatar =  imageRep.findByGender(gender)
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("No images in database"));
        imageRep.delete(imageAvatar);
        return imageAvatar;
    }

    public int getCountByGender(String gender)
    {
        return imageRep.findByGender(gender).size();
    }


    private String getFullImgPath(String imgName)
    {
        return String.format("%s/%s", dir, imgName);
    }
}

