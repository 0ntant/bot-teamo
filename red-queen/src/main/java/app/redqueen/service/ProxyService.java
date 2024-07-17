package app.redqueen.service;

import integration.dto.ProxyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyService
{
    public void setProxy(ProxyDto proxyDto)
    {
        if (proxyDto.getEnable())
        {
            System.setProperty("https.proxyHost", proxyDto.getIp());
            System.setProperty("https.proxyPort", String.valueOf(proxyDto.getPort()));
        }
        else
        {
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
        }
    }
}
