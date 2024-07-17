package auto.reg.service;

import integration.dto.ProxyDto;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ProxyService
{
    String proxyIpValue = "8888";
    String proxyPort = "127.0.0.1";
    boolean proxyEnable = false;

    public void setProxyValues(ProxyDto proxyDto)
    {
        if (proxyDto.getEnable())
        {
            proxyIpValue = proxyDto.getIp();
            proxyPort = proxyDto.getPort();
            proxyEnable = proxyDto.getEnable();

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
