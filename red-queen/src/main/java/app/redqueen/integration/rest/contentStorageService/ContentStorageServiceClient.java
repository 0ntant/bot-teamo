package app.redqueen.integration.rest.contentStorageService;

import app.redqueen.dto.integration.input.ImgDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentStorageServiceClient
{
    String getImgAvaUrl;
    String  getAvaImgCountUrl;

    RestOperations restOperations;

    public ImgDto getImgDtoByGender(String gender)
    {
        Map<String, String> params = new HashMap<>();
        params.put("gender", gender);
        try
        {
            ResponseEntity<ImgDto> response = restOperations.getForEntity(
                    getImgAvaUrl,
                    ImgDto.class,
                    params
            );
            return response.getBody();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public int getCountByGender(String gender)
    {
        Map<String, String> params = new HashMap<>();
        params.put("gender", gender);
        try
        {
            ResponseEntity<Integer> response = restOperations.getForEntity(
                    getAvaImgCountUrl,
                    Integer.class,
                    params
            );
            return response.getBody();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
