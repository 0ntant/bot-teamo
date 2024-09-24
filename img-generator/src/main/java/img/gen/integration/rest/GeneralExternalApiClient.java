package img.gen.integration.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Slf4j
@AllArgsConstructor
public class GeneralExternalApiClient
{
    protected final RestTemplate client;
    protected final RateLimiter rateLimiter;
    protected final GeneralClient generalClient;

    protected JsonNode rateLimitDecorator(String url)
    {
        Supplier<JsonNode> responseSup = RateLimiter.decorateSupplier(
                rateLimiter,
                ()-> executeGetRequest(url));
        return responseSup.get();
    }

    protected JsonNode executeGetRequest(String url)
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

    public int getAvailablePermissions()
    {
        return rateLimiter.getMetrics().getAvailablePermissions();
    }

    public byte[] getImage(String url)
    {
        return generalClient.getImage(url);
    }
}
