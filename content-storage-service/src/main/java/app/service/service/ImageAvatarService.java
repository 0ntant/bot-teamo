package app.service.service;

import app.service.mapper.ImageAvatarMapper;
import app.service.model.ImageAvatar;
import app.service.repository.ImageAvatarRepository;
import app.service.util.Base64Util;
import app.service.util.FileUtil;
import integration.dto.ImgAvaDto.ImgAvaDto;
import integration.dto.ImgAvaDto.Operation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static integration.dto.ImgAvaDto.Operation.SAVE;

@Slf4j
@Service
public class ImageAvatarService
{
    @Autowired
    ImageAvatarRepository imageRep;

    @Getter
    @Value("${file.images.avatar.dir}")
    String dir;

    @Autowired
    ObjectSumService objectSumService;

    int limitSizeMB = 10;

    public void saveFromImgGenerator(ImgAvaDto imgAvaDto)
    {
        switch (imgAvaDto.getOperation())
        {
            case SAVE -> saveImageAvatar(imgAvaDto);
            case REG -> regImageAvatar(imgAvaDto);
            default -> throw new UnsupportedOperationException("invalid avatar image operation");
        }
    }

    private void saveImageAvatar(ImgAvaDto imgAvaDto)
    {
        if (!isFileValidForSave(imgAvaDto))
        {
            return;
        }

        ImageAvatar imageAvatarToSave =
                ImageAvatarMapper.map(imgAvaDto, getFullImgPath(imgAvaDto.getName()));
        save(
                imageAvatarToSave,
                Base64Util.decode(imgAvaDto.getBase64().getBytes())
        );
    }

    private boolean isFileValidForSave(ImgAvaDto imgAvaDto)
    {
        byte[] fileData = Base64Util.decode(imgAvaDto.getBase64().getBytes());
        int fileDataSizeMB = fileData.length/1024/1024 ;
        if (fileDataSizeMB > limitSizeMB)
        {
            regImageAvatar(imgAvaDto);
            log.error("File {} too large to be teamo avatar", imgAvaDto.getName());
            return false;
        }
        return true;
    }

    private void regImageAvatar(ImgAvaDto imgAvaDto)
    {
        byte[] dataToReg = Base64Util.decode(
                imgAvaDto.getBase64().getBytes()
        );
        if (objectSumService.isObjectReg(dataToReg))
        {
            log.warn("Img {} already register" , imgAvaDto.getName());
            return;
        }
        log.info("Registration file {}", imgAvaDto.getName());
        objectSumService.regObject(dataToReg);
    }

    private void save(ImageAvatar imageAvatar, byte[] imgBytes)
    {
        objectSumService.save(imgBytes,imageAvatar);
        FileUtil.save(imageAvatar.getPath(), imgBytes);
    }

    public long count()
    {
        return imageRep.count();
    }

    public ImageAvatar getRandByGender(String gender)
    {
        ImageAvatar imageAvatar = imageRep.findByGender(gender)
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("No images in database"));
        imageAvatar.getObjectSum().setImageAvatar(null);
        imageRep.delete(imageAvatar);
        return imageAvatar;
    }

    @Transactional
    public byte[] getRandByGenderBytes(String gender)
    {
        ImageAvatar imageAvatar = imageRep.findByGender(gender)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No images in database"));
        byte[] imgBytes =  FileUtil.getBytes(imageAvatar.getPath());
        imageAvatar.getObjectSum().setImageAvatar(null);
        imageRep.delete(imageAvatar);
        FileUtil.remove(imageAvatar.getPath());
        return imgBytes;
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

