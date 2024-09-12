package img.gen.config;

import img.gen.integration.rest.UnsplashClient;
import img.gen.logging.LoggingRestClientInterceptor;
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

    @Bean
    public UnsplashClient unsplashClient()
    {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .defaultHeader(
                        "Authorization",
                        String.format("%s %s", "Client-ID", accessKey))
                .build();
        return UnsplashClient.builder()
                .client(restTemplate)
                .searchUrl(searchUrl)
                .randomUrl(randomUrl)
                .build();
    }
}
