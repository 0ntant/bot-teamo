package img.gen.service;

import img.gen.integration.rest.PixabayClient;
import img.gen.mapper.PexelsMapper;
import img.gen.mapper.PixabayMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PixabayService
{
    @Autowired
    PixabayClient client;

    @Autowired
    PhotoQueryService photoQueryServ;

    //min 3
    int perPage = 3;

    public List<byte[]> getMaleImages()
    {
        return getImages(photoQueryServ.getMale());
    }

    public List<byte[]> getFemaleImages()
    {
        return getImages(photoQueryServ.getFemale());
    }

    private List<byte[]> getImages(String gender)
    {
        log.info("Pixabay request={}", gender);
        List<String> photosUlrs = PixabayMapper.map(
                client.searchImages(gender, perPage)
        );
        List<byte[]> photosData = new ArrayList<>();
        for (String photoUlr : photosUlrs)
        {
            photosData.add(client.getImage(photoUlr));
        }
        return photosData;
    }

    public boolean isRateLimited()
    {
        return client.getAvailablePermissions() > perPage + 1;
    }
}
