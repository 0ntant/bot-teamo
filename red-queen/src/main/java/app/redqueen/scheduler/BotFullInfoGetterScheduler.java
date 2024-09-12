package app.redqueen.scheduler;

import app.redqueen.service.BotFullInfoGetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BotFullInfoGetterScheduler
{
    @Autowired
    BotFullInfoGetterService botFullInfoGetterService;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void requestFullInfo()
    {
        botFullInfoGetterService.getRequestUsersFromList();
    }
}
