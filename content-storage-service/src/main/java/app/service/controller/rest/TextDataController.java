package app.service.controller.rest;

import app.service.dto.input.TextDataTagSlice;
import app.service.dto.input.textDataDto.TextDataDto;
import app.service.mapper.TextDataMapper;
import app.service.model.DataTag;
import app.service.model.TextData;
import app.service.service.TextDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("text-data")
public class TextDataController
{
    @Autowired
    TextDataService textDataService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create")
    public void createTextData(@RequestBody TextDataDto textDataDto)
    {
        textDataService.save(TextDataMapper.map(textDataDto));
    }

    @GetMapping("get-by-tag-title/{tagTitle}")
    public String getByTagTitle(@PathVariable("tagTitle") String tagTitle)
    {
        return textDataService.getRandomByTagTitle(tagTitle).getTextContent();
    }

    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable("id") long id)
    {
        textDataService.delete(id);
    }

    @PostMapping("get/slice/title/tags")
    public List<app.service.dto.output.TextDataDto> getByTextContentSliceTitleAndTags(
            @RequestBody TextDataTagSlice textDataDto)
    {
        List<TextData> outputTextData;

        if (!textDataDto.tags().isEmpty())
        {
            outputTextData = textDataService.findLike(textDataDto.textSlice(),
                    textDataDto.tags()
                            .stream()
                            .map(title -> DataTag
                                    .builder()
                                    .title(title)
                                    .build()
                            )
                            .toList());
        }
        else
        {
            outputTextData = textDataService.findLike(textDataDto.textSlice());
        }
        return outputTextData
                .stream()
                .map(TextDataMapper::mapTextDataDto)
                .toList();
    }
}
