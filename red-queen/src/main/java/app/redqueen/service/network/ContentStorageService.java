package app.redqueen.service.network;

import app.redqueen.dto.integration.input.ImgDto;
import app.redqueen.integration.rest.contentStorageService.ContentStorageServiceClient;
import integration.dto.reg.ImageAvaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentStorageService
{
    @Autowired
    ContentStorageServiceClient cssClient;

    public ImageAvaDto getImgDtoByGender(String gender)
    {
        ImgDto imgDto =  cssClient.getImgDtoByGender(gender);
        return new ImageAvaDto(imgDto.getName(), imgDto.getBase64());
    }

    public int getCountByGender(String gender)
    {
        return cssClient.getCountByGender(gender);
    }
}
