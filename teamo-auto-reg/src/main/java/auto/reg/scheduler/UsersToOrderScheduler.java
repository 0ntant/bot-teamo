package auto.reg.scheduler;

import auto.reg.service.UsersOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsersToOrderScheduler
{
    @Autowired
    UsersOrderService usersOrderService;

    @Scheduled(fixedDelay = 1000 * 60)
    public void orderUser()
    {
        usersOrderService.regUserFromPool();
    }
}
