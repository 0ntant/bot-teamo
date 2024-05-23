package app.redqueen.controller.rest;

import app.redqueen.dto.input.botPhrase.PhraseDto;
import app.redqueen.dto.output.BotPhraseDto;
import app.redqueen.dto.output.BotPhraseTypeDto;
import app.redqueen.exception.ElementAlreadyExistsException;
import app.redqueen.model.BotPhrase;
import app.redqueen.model.BotPhraseType;
import app.redqueen.service.database.BotPhraseService;
import app.redqueen.service.database.BotPhraseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("bot-phrases")
public class BotPhrasesController
{
    @Autowired
    BotPhraseService phraseServ;

    @Autowired
    BotPhraseTypeService botPhraseTypeServ;

    @GetMapping("get/all")
    public List<BotPhraseDto> getAll()
    {
        return phraseServ.findAll()
                .stream()
                .map(BotPhraseDto::mapToBotPhraseTypeDto)
                .toList();
    }

    @GetMapping("get/{id}")
    public BotPhraseDto getGetById(@PathVariable("id") long id)
    {
        return BotPhraseDto.mapToBotPhraseTypeDto(
                phraseServ.findById(id)
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create")
    public BotPhraseDto create(@RequestBody PhraseDto botType)
    {
        String botPhraseBody = botType.getBody();
        if (phraseServ.isExistsByBody(botPhraseBody))
        {
            throw new ElementAlreadyExistsException(
                    String.format("BotPhrase body=%s",
                            botPhraseBody)
            );
        }
        BotPhraseType botPhraseType = botPhraseTypeServ.findById(botType.getType().getId());
        String gender = Objects.equals(botType.getGender(), "female") ? botType.getGender()  : "male";

        BotPhrase botPhraseToCreate = new BotPhrase();
        botPhraseToCreate.setBody(botPhraseBody);
        botPhraseToCreate.setBotPhraseType(botPhraseType);
        botPhraseToCreate.setGender(gender);
        phraseServ.save(botPhraseToCreate);

        return BotPhraseDto.mapToBotPhraseTypeDto(
                phraseServ.findByBody(botPhraseBody)
        );
    }


    @DeleteMapping("delete/{id}")
    public BotPhraseDto delete(@PathVariable("id") long id)
    {
        BotPhrase botPhraseToDelete = phraseServ.findById(id);
        phraseServ.delete(botPhraseToDelete);

        return BotPhraseDto.mapToBotPhraseTypeDto(
                botPhraseToDelete
        );
    }
}
