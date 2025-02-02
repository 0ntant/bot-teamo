package integration;

import java.util.Set;

public record TextDataDto(String textContent,
                          Set<DataTagDto> dataTags,
                          String createSource) {
}
