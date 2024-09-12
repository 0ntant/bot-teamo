package mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import img.gen.mapper.PexelsMapper;
import img.gen.mapper.PixabayMapper;
import img.gen.mapper.UnsplashMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest
{
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void unplashMapperPhotos_mapUrls()
    {
        //given
        File photos = new File("src/test/java/mapper/UnsplashPhotos");
        List<String> urls = new ArrayList<>();
        //then
        try
        {
            JsonNode json = objectMapper.readTree(photos);
            urls = UnsplashMapper.mapList(json);
        }catch (Exception ex)
        {ex.printStackTrace();}

        //expected
        assertEquals(10, urls.size());
        assertEquals(ArrayList.class, urls.getClass());
        assertEquals(String.class, urls.get(0).getClass());
    }

    @Test
    public void unplashMapperRandPhoto_mapUrl()
    {
        //given
        File randPhoto = new File("src/test/java/mapper/UnsplashRandomPhotos");
        String url = "";
        //than
        try
        {
            JsonNode json = objectMapper.readTree(randPhoto);
            url = UnsplashMapper.map(json);
        }catch (Exception ex)
        {ex.printStackTrace();}
        //expected
        assertEquals(
                "https://images.unsplash.com/photo-1558499932-9609acb6f443?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NDkyMTh8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MjUzMjY3NTV8&ixlib=rb-4.0.3&q=80&w=1080",
                url
        );
    }

    @Test
    public void pexelsMapper_mapUrl()
    {
        //given
        File randPhoto = new File("src/test/java/mapper/pexelsPhoto");
        String url = "";
        //then
        try
        {
            JsonNode json = objectMapper.readTree(randPhoto);
            url = PexelsMapper.map(json);
        }catch (Exception ex)
        {ex.printStackTrace();}
        //expected
        assertEquals("https://images.pexels.com/photos/1752806/pexels-photo-1752806.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                url
        );
    }

    @Test
    public void pixelbayMapper_mapUrl()
    {
        //given
        File jsonPhotos = new File("src/test/java/mapper/pixelbay");
        List<String> urls = new ArrayList<>();
        //then
        try
        {
            JsonNode json = objectMapper.readTree(jsonPhotos);
            urls = PixabayMapper.map(json);
        }catch (Exception ex)
        {ex.printStackTrace();}
        //expected
        assertEquals(3, urls.size());
        assertEquals(ArrayList.class, urls.getClass());
        assertEquals(String.class, urls.get(0).getClass());
    }
}
