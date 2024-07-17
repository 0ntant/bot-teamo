package app.provider.service;

import app.provider.integration.mq.ProxyListPublisher;
import app.provider.integration.rest.CheckerProxyIP;
import app.provider.model.ProxyHost;
import integration.dto.ProxyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProxyHostActuatorService
{
    @Autowired
    ProxyListPublisher proxyListPub;

    @Autowired
    CheckerProxyIP checkerProxyIP;

    List<ProxyHost> proxyHosts = new ArrayList<>();

    public void addProxyHost(ProxyHost proxyHost)
    {
        proxyHosts.add(proxyHost);
    }

    public void checkingProxy()
    {
        if (proxyHosts.isEmpty())
        {
            sendProxy(new ProxyDto("127.0.0.1", "8888", false));
        }
        else
        {
            ProxyHost lastProxyEl = proxyHosts.get(proxyHosts.size() - 1);
            if (checkerProxyIP.isProxyValid(lastProxyEl))
            {
                sendProxy(
                        new ProxyDto(
                            lastProxyEl.getIp(),
                            String.valueOf(lastProxyEl.getPort()),
                            true)
                );
            }
            else
            {
                proxyHosts.remove(lastProxyEl);
                checkingProxy();
            }
        }
    }

    private void sendProxy(ProxyDto proxyDto)
    {
        proxyListPub.sendProxyDto(proxyDto);
    }
}
