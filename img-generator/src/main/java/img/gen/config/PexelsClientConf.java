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
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;
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

    @Autowired
    Proxy proxy;

    @Bean
    PexelsClient pexelsClient(RestTemplateBuilder restTemplateBuilder)
    {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(200)
                .limitRefreshPeriod(Duration.ofHours(1L))
                .timeoutDuration(Duration.ZERO)
                .build();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(10));
        requestFactory.setReadTimeout(Duration.ofSeconds(10));
        requestFactory.setProxy(proxy);

        RestTemplate restTemplate = restTemplateBuilder
                .defaultHeader("Authorization", accessKey)
                .interceptors(new LoggingRestClientInterceptor())
                .build();
        restTemplate.setRequestFactory(requestFactory);

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
