package app.redqueen.config;

import app.redqueen.integration.rest.teamoAutoReg.UserAutoRegClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class UserAutoRegClientConfig
{
    @Value("${teamo-auto-reg.api.user-queue-size}")
    String getUserQueSizeUrl;

    @Bean
    public UserAutoRegClient userAutoRegClient(RestTemplateBuilder restTemplateBuilder)
    {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
        return new UserAutoRegClient(getUserQueSizeUrl, restTemplate);
    }
}
