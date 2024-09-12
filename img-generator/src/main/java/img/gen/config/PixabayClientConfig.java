package img.gen.config;

import img.gen.integration.rest.PixabayClient;
import img.gen.logging.LoggingRestClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class PixabayClientConfig
{
    @Value("${api.pixabay.search-url}")
    String searchUrl;

    @Value("${api.pixabay.access-key}")
    String accessKey;

    @Bean
    PixabayClient pixabayClient()
    {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .build();

        return PixabayClient.builder()
                .client(restTemplate)
                .accessKey(accessKey)
                .searchUrl(searchUrl)
                .build();
    }
}
