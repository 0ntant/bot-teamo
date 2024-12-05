package img.gen.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PhotoQueryService
{
    @Getter
    @Setter
    private List<String> maleRequests = List.of("male");

    @Getter
    @Setter
    private List<String> femaleRequests = List.of("female");

    Random random = new Random();

    String getMale()
    {
        return maleRequests.get(random.nextInt(maleRequests.size()));
    }

    String getFemale()
    {
        return femaleRequests.get(random.nextInt(femaleRequests.size()));
    }
}
