package app.provider.config;

import app.provider.integration.rest.CheckerProxyIP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@Configuration
public class CheckerProxyIPConfig
{
    @Value("#{'${urls-to-check-ip}'.split(',')}")
    List<String> urlsToCheckIp;

    @Bean
    CheckerProxyIP checkerProxyIP(RestTemplateBuilder restTemplateBuilder)
    {
        RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(Duration.ofSeconds(10))
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();

        return new CheckerProxyIP(restTemplate, urlsToCheckIp);
    }
}
