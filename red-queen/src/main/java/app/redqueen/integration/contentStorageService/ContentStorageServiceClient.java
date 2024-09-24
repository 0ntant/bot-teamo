package app.redqueen.integration.contentStorageService;

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
    String getAvaImgCountUrl;
    String regObjectUrl;
    String checkRegStatusUrl;

    RestOperations client;

    public ImgDto getImgDtoByGender(String gender)
    {
        Map<String, String> params = new HashMap<>();
        params.put("gender", gender);
        try
        {
            ResponseEntity<ImgDto> response = client.getForEntity(
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
            ResponseEntity<Integer> response = client.getForEntity(
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

    public String regObject(byte[] data)
    {
        try
        {
            return client.postForObject(
                    regObjectUrl,
                    data,
                    String.class
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public Boolean checkRegStatus(byte[] data)
    {
        try
        {
            return client.postForObject(
                    checkRegStatusUrl,
                    data,
                    Boolean.class
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
