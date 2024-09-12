package img.gen.service;

import img.gen.integration.rest.PixabayClient;
import img.gen.mapper.PexelsMapper;
import img.gen.mapper.PixabayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PixabayService
{
    @Autowired
    PixabayClient client;

    //min 3
    int perPage = 3;

    public List<byte[]> getMaleImages()
    {
        return getImages("man");
    }

    public List<byte[]> getFemaleImages()
    {
        return getImages("girl");
    }

    private List<byte[]> getImages(String gender)
    {
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
}
