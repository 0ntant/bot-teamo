package app.redqueen.controller.web;

import app.redqueen.bot.auto.BotScheduler;
import app.redqueen.dto.output.botUserData.BotUserDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "web/scheduler-bot")
public class BotSchedulerWebController
{
    @Autowired
    BotScheduler botScheduler;

    @GetMapping
    public String getIndex(Model model)
    {
        int userPoolSize = botScheduler.getUsersInPoolSize();
        String schedulerState = botScheduler.getSchedulerState();
        model.addAttribute("userPoolSize", userPoolSize);
        model.addAttribute("schedulerState", schedulerState);
        return "schedulerBot/index";
    }

    @GetMapping("get/user-pool")
    public String getUserPool(Model model)
    {
        List<BotUserDataDto> users = botScheduler.getListBotUserDataFromActiveBots()
                        .stream()
                        .map(BotUserDataDto::mapToBotUserDataDto)
                        .toList();
        model.addAttribute("users",users);
        return "schedulerBot/list";
    }
}
