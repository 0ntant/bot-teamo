package img.gen.integration.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;

@Component
public class PexelsClient extends GeneralExternalApiClient
{
    String searchUrl;

    @Builder
    public PexelsClient(RestTemplate client,
                         RateLimiter rateLimiter,
                         GeneralClient generalClient,
                         String searchUrl)
    {
        super(client, rateLimiter, generalClient);
        this.searchUrl = searchUrl;
    }

    public JsonNode searchPhoto(String query)
    {
        Random random = new Random();
        String urlTemplate = UriComponentsBuilder.fromUriString(searchUrl)
                .queryParam("per_page", "1")
                .queryParam("page", String.valueOf(random.nextInt(1, 8000)))
                .queryParam("query", query)
                .toUriString();
        return rateLimitDecorator(urlTemplate);
    }
}
