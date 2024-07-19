package app.provider.integration.rest;


import app.provider.model.ProxyHost;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class CheckerProxyIP
{
    private RestTemplate restOperations;
    private List<String> urlsToCheckIp;

    public boolean isProxyValid(ProxyHost proxyHost)
    {
        setProxy(proxyHost);
        boolean proxyHostValid = true;
        try
        {
            for (String url : urlsToCheckIp)
            {
                makeRequest(url);
            }
        }
        catch (Exception ex)
        {
            log.warn("Proxy={} invalid reason={}",
                    proxyHost.getProxy(),
                    ex.getMessage()
            );
            proxyHostValid = false;
        }

        return proxyHostValid;
    }

    private void setProxy(ProxyHost proxyHost)
    {
        SimpleClientHttpRequestFactory requestFactory
                = (SimpleClientHttpRequestFactory) restOperations.getRequestFactory();
        Proxy proxy = new Proxy(
                Type.HTTP,
                new InetSocketAddress(
                        proxyHost.getIp(),
                        proxyHost.getPort()
                )
        );

        requestFactory.setProxy(proxy);
        restOperations.setRequestFactory(requestFactory);
    }

    private void makeRequest(String url)
    {
        restOperations.getForObject(url, String.class, void.class);
    }
}
