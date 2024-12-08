package img.gen.config;

import img.gen.integration.rest.GeneralClient;
import img.gen.integration.rest.UnsplashClient;
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
public class UnsplashClientConf
{
    @Value("${api.unsplash.search-url}")
    String searchUrl;

    @Value("${api.unsplash.random-url}")
    String randomUrl;

    @Value("${api.unsplash.access-key}")
    String accessKey;

    @Autowired
    GeneralClient generalClient;

    @Bean
    public UnsplashClient unsplashClient(RestTemplateBuilder restTemplateBuilder)
    {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(50)
                .limitRefreshPeriod(Duration.ofHours(1L))
                .timeoutDuration(Duration.ZERO)
                .build();

        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .defaultHeader(
                        "Authorization",
                        String.format("%s %s", "Client-ID", accessKey))
                .build();

        return UnsplashClient.builder()
                .rateLimiter(RateLimiter.of(
                        "unsplashRateLimiter",
                        rateLimiterConfig))
                .client(restTemplate)
                .generalClient(generalClient)
                .searchUrl(searchUrl)
                .randomUrl(randomUrl)
                .build();
    }
}
