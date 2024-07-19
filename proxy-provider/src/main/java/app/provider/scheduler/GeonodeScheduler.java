package app.provider.scheduler;

import app.provider.service.GeonodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GeonodeScheduler
{
    @Autowired
    GeonodeService proxyService;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void addProxyList()
    {
        proxyService.addProxyList();
    }
}
