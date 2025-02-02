package img.gen.config;

import img.gen.integration.rest.GeneralClient;
import img.gen.integration.rest.PixabayClient;
import img.gen.logging.LoggingRestClientInterceptor;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;
import java.time.Duration;
import java.util.List;

@Configuration
public class PixabayClientConfig
{
    @Value("${api.pixabay.search-url}")
    String searchUrl;

    @Value("${api.pixabay.access-key}")
    String accessKey;

    @Autowired
    GeneralClient generalClient;

    @Autowired
    Proxy proxy;

    @Bean
    PixabayClient pixabayClient(RestTemplateBuilder restTemplateBuilder)
    {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofMinutes(1L))
                .timeoutDuration(Duration.ZERO)
                .build();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        requestFactory.setConnectTimeout(Duration.ofSeconds(10));
        requestFactory.setReadTimeout(Duration.ofSeconds(10));

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setInterceptors(
                List.of(
                        new LoggingRestClientInterceptor()
                )
        );

        return PixabayClient.builder()
                .rateLimiter(RateLimiter.of(
                        "pixabayRateLimiter",
                        rateLimiterConfig)
                )
                .generalClient(generalClient)
                .client(restTemplate)
                .accessKey(accessKey)
                .searchUrl(searchUrl)
                .build();
    }
}
