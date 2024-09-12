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


@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnsplashClient
{
    RestOperations client;

    @Autowired
    GeneralClient generalClient;

    String searchUrl;

    String randomUrl;

    public JsonNode getPhotos(String query, int page)
    {
        String urlTemplate = UriComponentsBuilder.fromUriString(searchUrl)
                .queryParam("page", String.valueOf(page))
                .queryParam("query", query)
                .queryParam("order_by", "latest")
                .toUriString();

        return executeGetRequest(urlTemplate);
    }

    public JsonNode getRandomPhotos(String query)
    {
        String urlTemplate = UriComponentsBuilder.fromUriString(randomUrl)
                .queryParam("query", query)
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
