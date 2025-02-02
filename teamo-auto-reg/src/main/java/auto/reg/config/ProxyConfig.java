package auto.reg.config;

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
    public Proxy netProxy()
    {
        return new Proxy(
                Proxy.Type.HTTP,
                new InetSocketAddress(
                        proxyHost,
                        proxyPort
                )
        );
    }

    @Bean
    public org.openqa.selenium.Proxy selProxy()
    {
        org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
        String proxyValue = String.format("%s:%s",
                proxyHost,
                proxyPort
        );
        proxy.setSslProxy(proxyValue);
        return proxy;
    }
}
