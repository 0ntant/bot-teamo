package img.gen.service;

import img.gen.integration.rest.UnsplashClient;
import img.gen.mapper.UnsplashMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("unsplash")
public class UnsplashService implements ImgProvideService
{
    @Autowired
    UnsplashClient client;

    @Autowired
    PhotoQueryService photoQueryServ;

    @Getter
    private final String source = "api.unsplash.com";

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
        log.info("Unsplash request={}", gender);
        String photoUlr = UnsplashMapper.map(
                client.getRandomPhotos(gender)
        );

        return client.getImage(photoUlr);
    }

    public boolean isRateLimited()
    {
        return client.getAvailablePermissions() > 0;
    }
}
