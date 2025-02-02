package img.gen.config;

import img.gen.integration.rest.GeneralClient;
import img.gen.logging.LoggingRestClientInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;
import java.time.Duration;

@Configuration
public class GeneralClientConfig
{
    @Autowired
    Proxy proxy;

    @Bean
    GeneralClient generalClient(RestTemplateBuilder restTemplateBuilder)
    {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        requestFactory.setReadTimeout(Duration.ofSeconds(10));
        requestFactory.setConnectTimeout(Duration.ofSeconds(10));

        RestTemplate restTemplate = restTemplateBuilder
                .defaultHeader(
                        "User-Agent",
                        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.5845.111 Safari/537.36"
                )
                .interceptors(new LoggingRestClientInterceptor())
                .build();
        restTemplate.setRequestFactory(requestFactory);
        return new GeneralClient(restTemplate);
    }
}
