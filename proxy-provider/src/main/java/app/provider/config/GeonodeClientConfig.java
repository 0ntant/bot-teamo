package app.provider.config;

import app.provider.integration.geonode.GeonodeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class GeonodeClientConfig
{
    @Value("${geonode.api.proxy-list}")
    String proxyListUrl;

    @Bean
    GeonodeClient geonodeClient(RestTemplateBuilder restTemplateBuilder)
    {
        RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(Duration.ofSeconds(10))
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();

        return new GeonodeClient(restTemplate, proxyListUrl);
    }
}
