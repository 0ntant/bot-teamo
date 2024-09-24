package img.gen.config;

import img.gen.integration.rest.CSSClient;
import img.gen.logging.LoggingRestClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class CSSClientConfig
{
    @Value("${inner-api.CCS.check}")
    String checkUrl;

    @Value("${inner-api.CCS.reg}")
    String regUrl;

    @Bean
    CSSClient cssClient()
    {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .build();

        return CSSClient.builder()
                .client(restTemplate)
                .checkUrl(checkUrl)
                .regUrl(regUrl)
                .build();
    }
}
