package app.redqueen.config;

import app.redqueen.integration.rest.contentStorageService.ContentStorageServiceClient;
import app.redqueen.logging.LoggingRestClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ContentStorageServiceClientConfig
{
    @Value("${content-storage-service.api.get-ava-img-base64-by-gender}")
    String getImgAvaUrl;

    @Value("${content-storage-service.api.get-ava-img-count-by-gender}")
    String  getAvaImgCountUrl;

    @Bean
    public ContentStorageServiceClient contentStorageServiceClient(RestTemplateBuilder restTemplateBuilder)
    {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .build();
        return ContentStorageServiceClient.builder()
                .getAvaImgCountUrl(getAvaImgCountUrl)
                .getImgAvaUrl(getImgAvaUrl)
                .restOperations(restTemplate)
                .build();
    }
}
