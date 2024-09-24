package img.gen.config;

import img.gen.integration.rest.GeneralClient;
import img.gen.integration.rest.PexelsClient;
import img.gen.logging.LoggingRestClientInterceptor;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class PexelsClientConf
{
    @Value("${api.pexels.search-url}")
    String searchUrl;

    @Value("${api.pexels.access-key}")
    String accessKey;

    @Autowired
    GeneralClient generalClient;

    @Bean
    PexelsClient pexelsClient()
    {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(200)
                .limitRefreshPeriod(Duration.ofHours(1L))
                .timeoutDuration(Duration.ZERO)
                .build();

        RestTemplate restTemplate = new RestTemplateBuilder()
                .defaultHeader("Authorization", accessKey)
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .build();

        return PexelsClient.builder()
                .rateLimiter(RateLimiter.of(
                        "pexelsRateLimiter",
                        rateLimiterConfig)
                )
                .client(restTemplate)
                .generalClient(generalClient)
                .searchUrl(searchUrl)
                .build();
    }
}
