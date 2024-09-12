package img.gen.config;

import img.gen.integration.rest.GeneralClient;
import img.gen.logging.LoggingRestClientInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class GeneralClientConfig
{
    @Bean
    GeneralClient generalClient()
    {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .defaultHeader(
                        "User-Agent",
                        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.5845.111 Safari/537.36"
                )
                .interceptors(new LoggingRestClientInterceptor())
                .build();
        return new GeneralClient(restTemplate);
    }
}
