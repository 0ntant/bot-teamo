package app.provider.service;

import app.provider.integration.rest.CheckerProxyIP;
import app.provider.model.ProxyHost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
@Slf4j
public class ProxyValidateService
{
    @Autowired
    ProxyHostActuatorService proxyHostActuatorServ;
    @Autowired
    CheckerProxyIP checkerProxyIP;

    @Async
    public void addToActuatorList(Queue<ProxyHost> proxyHosts )
    {
        while (!proxyHosts.isEmpty())
        {
            ProxyHost proxyHost = proxyHosts.poll();
            if (checkerProxyIP.isProxyValid(proxyHost))
            {
                proxyHostActuatorServ.addProxyHost(proxyHost);
            }
        }
        log.info("End of proxy list");
    }
}
