package app.service.mapper;

import app.service.dto.input.textDataDto.TextDataDto;
import app.service.model.TextData;

import java.time.LocalDateTime;

public class TextDataMapper
{
    public static TextData map(TextDataDto textDataDto)
    {
        return TextData.builder()
                .textContent(textDataDto.textContent())
                .createSource(textDataDto.createSource())
                .sysCreateDate(LocalDateTime.now())
                .tags(
                        textDataDto.dataTags()
                                .stream()
                                .map(DataTagMapper::map)
                                .toList()
                )
                .build();
    }

    public static TextData map(integration.TextDataDto textDataDto)
    {
        return TextData.builder()
                .textContent(textDataDto.textContent())
                .createSource(textDataDto.createSource())
                .sysCreateDate(LocalDateTime.now())
                .tags(
                        textDataDto.dataTags()
                                .stream()
                                .map(DataTagMapper::map)
                                .toList()
                )
                .build();
    }

    public static app.service.dto.output.TextDataDto mapTextDataDto(TextData textData)
    {
        return  app.service.dto.output.TextDataDto.builder()
                .id(textData.getId())
                .textContent(textData.getTextContent())
                .source(textData.getCreateSource())
                .build();
    }
}
