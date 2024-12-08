package app.redqueen.config;

import app.redqueen.integration.GeneralClient;
import app.redqueen.integration.imgGenerator.ImgGeneratorClient;
import app.redqueen.logging.LoggingRestClientInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class GeneralClientConfig
{
    @Bean
    GeneralClient generalClient(RestTemplateBuilder restTemplateBuilder)
    {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .build();
        return GeneralClient.builder()
                .client(restTemplate)
                .build();
    }
}
