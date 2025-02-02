package img.gen.service;

import img.gen.integration.rest.PexelsClient;
import img.gen.mapper.PexelsMapper;
import img.gen.mapper.UnsplashMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("Pexels")
public class PexelsService implements ImgProvideService
{
    @Autowired
    PexelsClient client;

    @Getter
    private final String source = "api.pexels.com";

    @Autowired
    PhotoQueryService photoQueryServ;

    public byte[] getMaleImage()
    {
        return getImage(photoQueryServ.getMale());
    }

    public byte[] getFemaleImage()
    {
        return getImage(photoQueryServ.getFemale());
    }

    private byte[] getImage(String gender)
    {
        log.info("Pexel request={}", gender);
        String photoUlr = PexelsMapper.map(
                client.searchPhoto(gender)
        );
        return client.getImage(photoUlr);
    }

    public boolean isRateLimited()
    {
        return client.getAvailablePermissions() > 0;
    }
}
