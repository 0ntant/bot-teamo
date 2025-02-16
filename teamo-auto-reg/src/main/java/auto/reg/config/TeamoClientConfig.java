package auto.reg.config;

import auto.reg.integration.rest.teamo.TeamoClient;
import auto.reg.logging.LoggingRestClientInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;
import java.time.Duration;
import java.util.List;

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
    @Autowired
    Proxy proxy;

    @Bean
    TeamoClient teamoClient(RestTemplateBuilder restTemplateBuilder)
    {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(10));
        requestFactory.setReadTimeout(Duration.ofSeconds(10));
        requestFactory.setProxy(proxy);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setInterceptors(
                List.of(
                        new LoggingRestClientInterceptor()
                )
        );

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
