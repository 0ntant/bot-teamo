package app.redqueen.controller.web;

import app.redqueen.dto.output.BotPhraseTypeDto;
import app.redqueen.service.database.BotPhraseService;
import app.redqueen.service.database.BotPhraseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("bot-phrases-type")
public class BotPhrasesTypeWebController
{
    @Autowired
    BotPhraseService botPhraseServ;

    @Autowired
    BotPhraseTypeService botPhraseTypeServ;

    @GetMapping
    public String getIndex()
    {
        return "botPhrasesType/index";
    }

    @GetMapping("get/list-all")
    public String getListAll(Model model)
    {
        List<BotPhraseTypeDto> botPhraseTypes = new ArrayList<>(
                botPhraseTypeServ.findAll()
                        .stream()
                        .map(BotPhraseTypeDto::mapToBotPhraseType)
                        .toList());
        model.addAttribute("botPhraseTypes", botPhraseTypes);
        return "botPhrasesType/list";
    }

    @GetMapping("get/upload-form")
    public String getUploadForm(Model model)
    {
        return "botPhrasesType/uploadForm";
    }
}
