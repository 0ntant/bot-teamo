package img.gen.integration.rest;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;

@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PixabayClient
{
    RestOperations client;

    String accessKey;

    String searchUrl;

    @Autowired
    GeneralClient generalClient;

    public JsonNode searchImages(String query, int perPage)
    {
        Random random = new Random();
        String urlTemplate = UriComponentsBuilder.fromUriString(searchUrl)
                .queryParam("key", accessKey)
                .queryParam("per_page", String.valueOf(perPage))
                .queryParam("page", String.valueOf(random.nextInt(1, 150)))
                .queryParam("q", query)
                .queryParam("pretty", "true")
                .queryParam("image_type", "photo")
                .toUriString();

        return executeGetRequest(urlTemplate);
    }

    private JsonNode executeGetRequest(String url)
    {
        JsonNode response = null;
        try
        {
            response = client.getForObject(
                    url,
                    JsonNode.class
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return response;
    }

    public byte[] getImage(String url)
    {
        return generalClient.getImage(url);
    }
}
