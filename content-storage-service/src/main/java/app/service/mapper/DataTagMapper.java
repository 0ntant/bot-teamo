package app.service.mapper;

import app.service.dto.input.textDataDto.DataTagDto;
import app.service.model.DataTag;


public class DataTagMapper
{
    public static DataTag map(DataTagDto tagDto)
    {
        return DataTag.builder()
                .title(tagDto.title())
                .build();
    }

    public static DataTag map(integration.DataTagDto dataTagDto)
    {
        return DataTag.builder()
                .title(dataTagDto.title())
                .build();
    }
}
