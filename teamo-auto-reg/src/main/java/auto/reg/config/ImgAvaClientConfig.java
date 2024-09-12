package auto.reg.config;

import auto.reg.integration.rest.contentStorageService.ImgAvaClient;
import auto.reg.logging.LoggingRestClientInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ImgAvaClientConfig
{
    @Value("${content-storage-serviceAPI.countByGender}")
    String countByGenderUrl;
    @Value("${content-storage-serviceAPI.infoBase64ByGender}")
    String infoBase64ByGenderUrl;

    @Bean
    ImgAvaClient imgAvaClient(RestTemplateBuilder restTemplateBuilder)
    {
        RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(Duration.ofSeconds(10))
                .setConnectTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .build();
        return  ImgAvaClient.builder()
                .restTemplate(restTemplate)
                .objectMapper(new ObjectMapper())
                .countByGenderUrl(countByGenderUrl)
                .infoBase64ByGenderUrl(infoBase64ByGenderUrl)
                .build();
    }
}
