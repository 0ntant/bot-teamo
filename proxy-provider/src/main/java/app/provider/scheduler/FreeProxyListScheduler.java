package app.provider.scheduler;

import app.provider.integration.rest.CheckerProxyIP;
import app.provider.model.ProxyHost;
import app.provider.service.FreeProxyListService;
import app.provider.service.ProxyHostActuatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Queue;

@EnableAsync
@Component
@Slf4j
public class FreeProxyListScheduler
{
    @Autowired
    FreeProxyListService proxyService;

    @Autowired
    CheckerProxyIP checkerProxyIP;

    @Autowired
    ProxyHostActuatorService proxyHostActuatorServ;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 30 )
    public void addProxyList()
    {
        Queue<ProxyHost> proxyHosts = proxyService.getProxyHostsList();
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
