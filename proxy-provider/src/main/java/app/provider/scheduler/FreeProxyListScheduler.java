package app.provider.scheduler;

import app.provider.service.FreeProxyListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@EnableAsync
@Component
@Slf4j
public class FreeProxyListScheduler
{
    @Autowired
    FreeProxyListService proxyService;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void addProxyList()
    {
       proxyService.addProxyList();
    }

}
