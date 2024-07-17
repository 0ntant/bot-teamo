package app.redqueen.config;

import app.redqueen.integration.rest.teamo.RtestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RtestClientConfig
{
    @Value("${teamo.api.teamo.post.getLocation}")
    String getLocationUrl;

    @Bean
    RtestClient rtestClient(RestTemplateBuilder restTemplateBuilder)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();

        return RtestClient.builder()
                .objectMapper(objectMapper)
                .getLocationUrl(getLocationUrl)
                .restOperations(restTemplate)
                .build();
    }
}
