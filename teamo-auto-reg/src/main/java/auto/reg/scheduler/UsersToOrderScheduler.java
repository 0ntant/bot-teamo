package auto.reg.scheduler;

import auto.reg.service.UsersToOrderQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsersToOrderScheduler
{
    @Autowired
    UsersToOrderQueueService usersToOrderQueueService;

    @Scheduled(fixedDelay = 1000 * 60)
    public void orderUser()
    {
        if (usersToOrderQueueService.getUserQueSize() > 0)
        {
            log.info("Order user");
            usersToOrderQueueService.regUser();
        }
    }
}
