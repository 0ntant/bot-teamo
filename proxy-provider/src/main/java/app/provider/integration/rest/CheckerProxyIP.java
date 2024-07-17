package app.provider.integration.rest;


import app.provider.model.ProxyHost;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class CheckerProxyIP
{
    private RestOperations restOperations;
    private List<String> urlsToCheckIp;

    public boolean isProxyValid(ProxyHost proxyHost)
    {
        boolean proxyHostValid = true;
        System.setProperty("https.proxyHost", proxyHost.getIp());
        System.setProperty("https.proxyPort", String.valueOf(proxyHost.getPort()));
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
        finally
        {
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
        }
        return proxyHostValid;
    }

    private void makeRequest(String url)
    {
        restOperations.getForObject(url, String.class, void.class);
    }
}
