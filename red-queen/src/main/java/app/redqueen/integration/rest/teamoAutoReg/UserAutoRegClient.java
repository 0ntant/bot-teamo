package app.redqueen.integration.rest.teamoAutoReg;

import app.redqueen.dto.integration.input.ImgDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserAutoRegClient
{
    String getUserQueSizeUrl;
    RestOperations restOperations;

    public int getUserQueSize()
    {
        try
        {
            ResponseEntity<Integer> response = restOperations.getForEntity(getUserQueSizeUrl, Integer.class);
            return response.getBody();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
