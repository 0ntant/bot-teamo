package app.redqueen.controller.rest;

import app.redqueen.bot.auto.BotScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "scheduler-bot")
public class BotSchedulerController
{
    @Autowired
    BotScheduler botScheduler;

    @GetMapping("start")
    private String startScheduler() {
        if (botScheduler.isRunning()) {
            return "Scheduler already started";
        }
        else
        {
            botScheduler.start();
        }

        return "Scheduler started";
    }
    @GetMapping("state")
    private String getState()
    {
        if (botScheduler.isRunning())
        {
            return "Scheduler online";
        }
        else
        {
            return "Scheduler offline";
        }
    }

    @GetMapping("stop")
    private String stopScheduler()
    {
        if (botScheduler.isRunning())
        {
            botScheduler.stop();
        }
        else
        {
            return "Scheduler in not started";
        }
        return "Scheduler stopped";
    }

    @GetMapping("bot-pool-size")
    private int getSchedulerBotPoolSize()
    {
        return botScheduler.getUsersInPoolSize();
    }

}
