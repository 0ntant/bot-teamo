package img.gen.integration.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@Component
public class UnsplashClient extends GeneralExternalApiClient
{
    String searchUrl;

    String randomUrl;

    @Builder
    public UnsplashClient(RestTemplate client,
                         RateLimiter rateLimiter,
                         GeneralClient generalClient,
                         String randomUrl,
                         String searchUrl)
    {
        super(client, rateLimiter, generalClient);
        this.randomUrl = randomUrl;
        this.searchUrl = searchUrl;
    }

    public JsonNode getPhotos(String query, int page)
    {
        String urlTemplate = UriComponentsBuilder.fromUriString(searchUrl)
                .queryParam("page", String.valueOf(page))
                .queryParam("query", query)
                .queryParam("order_by", "latest")
                .toUriString();

        return rateLimitDecorator(urlTemplate);
    }

    public JsonNode getRandomPhotos(String query)
    {
        String urlTemplate = UriComponentsBuilder.fromUriString(randomUrl)
                .queryParam("query", query)
                .toUriString();

        return rateLimitDecorator(urlTemplate);
    }
}
