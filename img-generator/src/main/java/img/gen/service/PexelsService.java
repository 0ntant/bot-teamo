package img.gen.service;

import img.gen.integration.rest.PexelsClient;
import img.gen.mapper.PexelsMapper;
import img.gen.mapper.UnsplashMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PexelsService
{
    @Autowired
    PexelsClient client;

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
