package auto.reg.service;

import auto.reg.util.Base64Util;
import auto.reg.util.TempFileUtil;
import integration.dto.ImgAvaDto;
import integration.dto.reg.ImageAvaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TempFileService
{
    @Value("${file.photos.dir}")
    private String photosDir;

    public String saveFromImgAvaDto(ImgAvaDto imgAvaDto)
    {
        return TempFileUtil.save(
                getFilePathByName(imgAvaDto.getName()),
                Base64Util.decode(imgAvaDto.getBase64().getBytes())
        );
    }

    public String saveFromImageAvaDto(ImageAvaDto imgAvaDto)
    {
        return TempFileUtil.save(
                getFilePathByName(imgAvaDto.getName()),
                Base64Util.decode(imgAvaDto.getBase64().getBytes())
        );
    }

    public void remove(String filePath)
    {
        TempFileUtil.remove(filePath);
    }

    public void removeByName(String fileName)
    {
        TempFileUtil.remove(getFilePathByName(fileName));
    }

    public String getFilePathByName(String name)
    {
        return String.format("%s/%s", photosDir, name);
    }
}
