package img.gen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class ProxyConfig
{
    @Value("${proxy.http.port}")
    Integer proxyPort;
    @Value("${proxy.http.host}")
    String proxyHost;

    @Bean
    public Proxy proxy()
    {
        return new Proxy(
                Proxy.Type.HTTP,
                new InetSocketAddress(
                        proxyHost,
                        proxyPort
                )
        );
    }
}
