package img.gen.integration.rest;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CSSClient
{
    RestOperations client;
    String checkUrl;
    String regUrl;

    public boolean checkObjectExists(byte[] data)
    {
        Boolean response = false;
        try
        {
            response = client.postForObject(
                    checkUrl,
                    data,
                    Boolean.class
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return response;
    }

    public String regObject(byte[] data)
    {
        String response = "";
        try
        {
            response = client.postForObject(
                    regUrl,
                    data,
                    String.class
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return response;
    }
}
