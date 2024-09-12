package img.gen.integration.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PersonNotExistImgClient
{
    RestTemplate client = new RestTemplate();

    @Value("${api.personotexistimg}")
    String url;

    public byte[] getImage()
    {
        return client.getForObject(url, byte[].class);
    }
}
