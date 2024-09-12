package auto.reg.config;

import auto.reg.integration.rest.teamo.TeamoClient;
import auto.reg.logging.LoggingRestClientInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class TeamoClientConfig
{
    @Value("${teamoAPI.nextStep}")
    String nextStepUrl;
    @Value("${teamoAPI.editMainInfo}")
    String editMainInfoUrl;
    @Value("${teamoAPI.cancelPsychoTesting}")
    String cancelPsychoTestingUrl;
    @Value("${teamoAPI.skipConfirmation}")
    String skipConfirmationUrl;

    @Bean
    TeamoClient teamoClient(RestTemplateBuilder restTemplateBuilder)
    {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors(new LoggingRestClientInterceptor())
                .build();
        return TeamoClient.builder()
                .restTemplate(restTemplate)
                .objectMapper(new ObjectMapper())
                .nextStepUrl(nextStepUrl)
                .editMainInfoUrl(editMainInfoUrl)
                .cancelPsychoTestingUrl(cancelPsychoTestingUrl)
                .skipConfirmationUrl(skipConfirmationUrl)
                .build();
    }
}
