package app.provider.scheduler;

import app.provider.service.ProxyscrapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProxyscrapeScheduler
{
    @Autowired
    ProxyscrapeService proxyService;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 15)
    public void addProxyList()
    {
        proxyService.addProxyList();
    }
}
