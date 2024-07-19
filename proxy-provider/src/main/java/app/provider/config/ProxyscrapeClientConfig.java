package app.provider.config;

import app.provider.integration.geonode.GeonodeClient;
import app.provider.integration.proxyscrape.ProxyscrapeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ProxyscrapeClientConfig
{
    @Value("${proxyscrape.api.proxy-list}")
    String proxyListUrl;

    @Bean
    ProxyscrapeClient proxyscrapeClient(RestTemplateBuilder restTemplateBuilder)
    {
        RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(Duration.ofSeconds(10))
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();

        return new ProxyscrapeClient(restTemplate, proxyListUrl);
    }

}
