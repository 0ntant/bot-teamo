package app.service.service;

import app.service.mapper.ImageAvatarMapper;
import app.service.model.ImageAvatar;
import app.service.repository.ImageAvatarRepository;
import app.service.util.FileUtil;
import integration.dto.ImgAvaDto.ImgAvaDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


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

    @Autowired
    FileValidatorService fileValidatorServ;

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

        if (objectSumService.isObjectReg(imgAvaDto.getImgData()))
        {
            log.warn("Element already exists");
            return;
        }
        save(
                imageAvatarToSave,
                imgAvaDto.getImgData()
        );
    }

    private boolean isFileValidForSave(ImgAvaDto imgAvaDto)
    {
        byte[] fileData = imgAvaDto.getImgData();
        int fileDataSizeMB = fileData.length/1024/1024;
        if (fileDataSizeMB > limitSizeMB)
        {
            regImageAvatar(imgAvaDto);
            log.error(
                    "File {} too large to be teamo avatar",
                    imgAvaDto.getName()
            );
            return false;
        }
        return true;
    }

    private void regImageAvatar(ImgAvaDto imgAvaDto)
    {
        byte[] dataToReg = imgAvaDto.getImgData();
        if (objectSumService.isObjectReg(dataToReg))
        {
            log.warn("Img {} already register", imgAvaDto.getName());
            return;
        }
        log.info("Registration file {}", imgAvaDto.getName());
        objectSumService.regObject(dataToReg);
    }

    private void save(ImageAvatar imageAvatar, byte[] imgBytes)
    {
        if(!fileValidatorServ.isImgValid(imgBytes))
        {
            log.error("Image avatar file invalid {}", imageAvatar.getPath());
            return;
        }
        objectSumService.save(imgBytes, imageAvatar);
        FileUtil.write(imageAvatar.getPath(), imgBytes);
    }

    public long count()
    {
        return imageRep.count();
    }

    public ImageAvatar getRandImgAvatarByGender(String gender)
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
    public byte[] getRandImgBytesByGender(String gender)
    {
        ImageAvatar imageAvatar = imageRep.findByGender(gender)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No images in database"));
        byte[] imgBytes = FileUtil.getBytes(imageAvatar.getPath());
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

