package img.gen.service;

import img.gen.integration.contentStorageService.CSSClient;
import img.gen.integration.contentStorageService.ContentStoragePub;
import integration.dto.ImgAvaDto.ImgAvaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentStorageService
{
    @Autowired
    ContentStoragePub contentStoragePub;

    @Autowired
    CSSClient cssClient;

    public void sendImg(ImgAvaDto imgAvaDto)
    {
        contentStoragePub.sendMsq(imgAvaDto);
    }

    public boolean isReg(byte[] data)
    {
        return cssClient.checkObjectExists(data);
    }
}
