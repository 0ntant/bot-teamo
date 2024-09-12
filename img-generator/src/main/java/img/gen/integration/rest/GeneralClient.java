package img.gen.integration.rest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class GeneralClient
{
    RestTemplate client;

    public byte[] getImage(String url)
    {
        return client.getForObject(url, byte[].class);
    }
}
