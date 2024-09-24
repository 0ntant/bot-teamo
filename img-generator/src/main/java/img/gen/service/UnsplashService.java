package img.gen.service;

import img.gen.integration.rest.UnsplashClient;
import img.gen.mapper.UnsplashMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnsplashService
{
    @Autowired
    UnsplashClient client;

    public byte[] getMaleImage()
    {
       return getImage("man");
    }

    public byte[] getFemaleImage()
    {
        return getImage("girl");
    }

    private byte[] getImage(String gender)
    {
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
