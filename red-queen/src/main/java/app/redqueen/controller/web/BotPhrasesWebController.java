package app.redqueen.controller.web;

import app.redqueen.dto.output.BotPhraseDto;
import app.redqueen.dto.output.BotPhraseTypeDto;
import app.redqueen.service.database.BotPhraseService;
import app.redqueen.service.database.BotPhraseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("bot-phrases")
@Controller
public class BotPhrasesWebController
{
    @Autowired
    BotPhraseService botPhraseServ;

    @Autowired
    BotPhraseTypeService botPhraseTypeServ;

    @GetMapping
    public String getIndex()
    {
        return "botPhrases/index";
    }

    @GetMapping("get/list-all")
    public String getListAll(Model model)
    {
        List<BotPhraseDto> botPhrases = new ArrayList<>(
                botPhraseServ.findAll()
                    .stream()
                    .map(BotPhraseDto::mapToBotPhraseTypeDto)
                    .toList()
        );
        Collections.reverse(botPhrases);
        model.addAttribute("botPhrases", botPhrases);
        return "botPhrases/list";
    }

    @GetMapping("get/list/by-type-title")
    public String getListByTypeTitle(Model model, @RequestParam("typeTitle") String typeTitle)
    {
        List<BotPhraseDto> botPhrases = new ArrayList<>(
                botPhraseServ.findByBotPhraseTypeTitle(typeTitle)
                    .stream()
                    .map(BotPhraseDto::mapToBotPhraseTypeDto)
                    .toList()
        );
        Collections.reverse(botPhrases);
        model.addAttribute("botPhrases", botPhrases);
        return "botPhrases/list";
    }

    @GetMapping("get/upload-form")
    public String getUploadForm(Model model)
    {
        List<BotPhraseTypeDto> botPhraseTypes
                = botPhraseTypeServ.findAll()
                        .stream()
                        .map(BotPhraseTypeDto::mapToBotPhraseType)
                        .toList();
        model.addAttribute("botPhraseTypes", botPhraseTypes);
        return "botPhrases/uploadForm";
    }
}

