package img.gen.service;

import img.gen.integration.rest.PersonNotExistImgClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PEN")
public class PENService implements ImgProvideService
{
    @Getter
    private final String source = "thispersondoesnotexist.com";

    @Autowired
    ImgProcessingService imgProcessingService;

    @Autowired
    PersonNotExistImgClient imgPENClient;

    @Override
    public byte[] getMaleImage() {
        return getImg();
    }

    @Override
    public byte[] getFemaleImage() {
        return getImg();
    }

    private byte[] getImg()
    {
        return imgProcessingService.cutBottom(
                imgPENClient.getImage(),
                25
        );
    }

    @Override
    public boolean isRateLimited() {
        return false;
    }
}
