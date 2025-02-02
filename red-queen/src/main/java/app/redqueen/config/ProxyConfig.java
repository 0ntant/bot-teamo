package app.redqueen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class ProxyConfig
{
    @Value("${proxy.http.host}")
    private String proxyHost;

    @Value("${proxy.http.port}")
    private Integer proxyPort;

    private static String staticProxyHost;
    private static Integer staticProxyPort;

    @Value("${proxy.http.host}")
    public void setStaticProxyHost(String proxyHost) {
        ProxyConfig.staticProxyHost = proxyHost;
    }

    @Value("${proxy.http.port}")
    public void setStaticProxyPort(Integer proxyPort) {
        ProxyConfig.staticProxyPort = proxyPort;
    }

    public static String getProxyHost() {
        return staticProxyHost;
    }

    public static Integer getProxyPort() {
        return staticProxyPort;
    }
}
