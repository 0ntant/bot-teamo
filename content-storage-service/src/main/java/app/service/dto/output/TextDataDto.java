package app.service.dto.output;

import lombok.Builder;

@Builder
public record TextDataDto(long id, String textContent, String source) {
}
