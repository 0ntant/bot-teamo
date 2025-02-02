package app.service.dto.input.textDataDto;

import lombok.Builder;

import java.util.Set;

@Builder
public record TextDataDto (String textContent,
                           Set<DataTagDto> dataTags,
                           String createSource){
}
