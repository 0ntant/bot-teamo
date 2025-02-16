package app.redqueen.scheduler;

import app.redqueen.service.BotOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class BotOrderScheduler
{
    @Autowired
    BotOrderService botOrderService;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 30 )
    public void orderUser()
    {
        botOrderService.orderUser();
    }
}
