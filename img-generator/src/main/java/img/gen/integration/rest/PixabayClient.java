package img.gen.integration.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;

@Component
public class PixabayClient extends GeneralExternalApiClient
{
    String accessKey;
    String searchUrl;

    @Builder
    public PixabayClient(RestTemplate client,
                         RateLimiter rateLimiter,
                         GeneralClient generalClient,
                         String accessKey,
                         String searchUrl)
    {
        super(client, rateLimiter, generalClient);
        this.accessKey = accessKey;
        this.searchUrl = searchUrl;
    }

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
        return rateLimitDecorator(urlTemplate);
    }
}
