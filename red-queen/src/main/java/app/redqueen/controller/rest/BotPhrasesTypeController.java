package app.redqueen.controller.rest;

import app.redqueen.dto.input.BotPhrasesTypeCreateDto;
import app.redqueen.dto.output.BotPhraseTypeDto;
import app.redqueen.exception.ElementAlreadyExistsException;
import app.redqueen.model.BotPhraseType;
import app.redqueen.service.database.BotPhraseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("bot-phrases-type")
public class BotPhrasesTypeController
{
    @Autowired
    BotPhraseTypeService botPhraseTypeServ;

    @GetMapping("get/all")
    public List<BotPhraseTypeDto> getAll()
    {
        return botPhraseTypeServ.findAll()
                .stream()
                .map(BotPhraseTypeDto::mapToBotPhraseType)
                .toList();
    }

    @GetMapping("get/{id}")
    public BotPhraseTypeDto getById(@PathVariable("id") long id)
    {
        return BotPhraseTypeDto.mapToBotPhraseType(
                botPhraseTypeServ.findById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create")
    public BotPhraseTypeDto create(@RequestBody BotPhrasesTypeCreateDto botPhrasesTypeCreate)
    {
        String botPhrasesTypeTitle = botPhrasesTypeCreate.getTitle();
        if (botPhraseTypeServ.isExitsByTitle(botPhrasesTypeTitle))
        {
            throw new ElementAlreadyExistsException(
                    String.format("BotPhraseType title=%s already exits",
                            botPhrasesTypeTitle
                    )
            );
        }
        BotPhraseType botPhraseTypeToCreate = new BotPhraseType();
        botPhraseTypeToCreate.setTitle(botPhrasesTypeTitle);
        botPhraseTypeServ.save(botPhraseTypeToCreate);

        return BotPhraseTypeDto.mapToBotPhraseType(
                botPhraseTypeServ.findByTitle(botPhrasesTypeTitle)
        );
    }
}
