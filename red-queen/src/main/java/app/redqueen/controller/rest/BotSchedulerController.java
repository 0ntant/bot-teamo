package app.redqueen.controller.rest;

import app.redqueen.bot.auto.BotScheduler;
import app.redqueen.dto.input.BotSchPhraseTypeDto;
import app.redqueen.dto.output.BotSchedulerPhraseTypeDto;
import app.redqueen.model.BotPhrase;
import app.redqueen.model.BotPhraseType;
import app.redqueen.service.database.BotPhraseService;
import app.redqueen.service.database.BotPhraseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path = "scheduler-bot")
public class BotSchedulerController
{
    @Autowired
    BotScheduler botScheduler;

    @GetMapping("start")
    public String startScheduler() {
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
    public String getState()
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
    public String stopScheduler()
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

    @GetMapping("get/bot-pool-size")
    public int getSchedulerBotPoolSize()
    {
        return botScheduler.getUsersInPoolSize();
    }

    @GetMapping("get/phrases-type")
    public List<BotSchedulerPhraseTypeDto> getSchedulerBotPhrasesType()
    {
        return getSchedulerBotPhrasesTypeList();
    }

    @PostMapping("edit/phrases-type")
    public List<BotSchedulerPhraseTypeDto> editSchedulerBotPhrasesType(
            @RequestBody List<BotSchPhraseTypeDto> botTypePhrases
    )
    {
        botScheduler.setDefaultDialogTypeTitles(
                botTypePhrases
                        .stream()
                        .map(BotSchPhraseTypeDto::map)
                        .toList()
        );
        return getSchedulerBotPhrasesTypeList();
    }

    private List<BotSchedulerPhraseTypeDto> getSchedulerBotPhrasesTypeList()
    {
        AtomicInteger counter = new AtomicInteger(0);
        return botScheduler.getDefaultDialogTypeTitles()
                .stream()
                .map(botPhraseType -> BotSchedulerPhraseTypeDto.map(
                        counter.getAndIncrement(),
                        botPhraseType)
                )
                .toList();
    }
}
