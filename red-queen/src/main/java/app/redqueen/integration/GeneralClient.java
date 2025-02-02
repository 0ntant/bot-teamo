package app.redqueen.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralClient
{
    @Setter
    RestTemplate client;

    public byte[] getFileData(String url)
    {
        try
        {
            return client.getForObject(url, byte[].class);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
