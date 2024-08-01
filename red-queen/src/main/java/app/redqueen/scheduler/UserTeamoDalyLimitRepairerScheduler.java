package app.redqueen.scheduler;

import app.redqueen.service.UserTeamoDalyLimitRepairerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserTeamoDalyLimitRepairerScheduler
{
    @Autowired
    UserTeamoDalyLimitRepairerService userRepairerServ;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 12)
    public void repairUsers()
    {
        log.info("Start repair users by daly limit");
        userRepairerServ.unblockBotsByDalyLimit();
    }
}
