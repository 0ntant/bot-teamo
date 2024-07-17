package app.provider.scheduler;

import app.provider.service.ProxyHostActuatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class ProxyActuatorScheduler
{
    @Autowired
    ProxyHostActuatorService proxyHostActuatorService;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 3)
    public void checkProxy()
    {
        proxyHostActuatorService.checkingProxy();
    }

}
