package app.service;

import app.integration.contentStorageService.TextDataPublisher;
import integration.DataTagDto;
import integration.TextDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContentStorageService
{
    final private TextDataPublisher textDataPublisher;

    public void sendJoke(String joke, String createSource)
    {
        Set<DataTagDto> tags = Set.of(new DataTagDto("joke"));
        TextDataDto textDataDto
                = new TextDataDto(joke, tags, createSource);
        textDataPublisher.sendRegUserTeamo(textDataDto);
    }
}
