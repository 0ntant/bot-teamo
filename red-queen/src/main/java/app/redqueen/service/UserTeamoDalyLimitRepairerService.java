package app.redqueen.service;

import app.redqueen.model.UserTeamo;
import app.redqueen.repository.UserTeamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserTeamoDalyLimitRepairerService
{
    int hoursLimit = 25;

    @Autowired
    UserTeamoRepository userTeamoRepository;

    public void unblockBotsByDalyLimit()
    {
        List<UserTeamo> bannedBots = dailyLimitBannedBots();
        for(UserTeamo bannedBot : bannedBots)
        {
            if (isDalyLimitOver(bannedBot))
            {
                log.info("Bot id={} repair", bannedBot.getId());
                unblockBot(bannedBot);
            }
        }
    }

    private List<UserTeamo> dailyLimitBannedBots()
    {
        return userTeamoRepository.findBotsBlockingByReason("Encounter day limit");
    }

    private boolean isDalyLimitOver(UserTeamo userTeamo)
    {
        Date nowDate = new Date() ;
        Date botBlockDate = userTeamo.getBlock().getBlockDate();
        long diffInMil = Math.abs(nowDate.getTime() - botBlockDate.getTime());
        long diff = TimeUnit.HOURS.convert(diffInMil, TimeUnit.MILLISECONDS);
        return diff > hoursLimit;
    }

    private void unblockBot(UserTeamo userTeamo)
    {
        userTeamo.getBlock().setIsBlocking(false);
        userTeamoRepository.save(userTeamo);
    }
}
