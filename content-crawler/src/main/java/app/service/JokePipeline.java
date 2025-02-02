package app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JokePipeline implements Pipeline
{
    private final SimpleJokeRecognizer simpleJokeRecognizer;
    private final  ContentStorageService contentStorageService;

    @Override
    public void process(ResultItems resultItems, Task task)
    {
        List<String> textList = resultItems.get("text");
        for (String srt: textList)
        {
            processText(srt, resultItems);
        }
    }

    public void processText(String text, ResultItems resultItems)
    {
        if (text == null || text.isEmpty()) {
            return;
        }
        boolean isJoke = simpleJokeRecognizer.isJoke(text);

        if (isJoke)
        {
            contentStorageService.sendJoke(text, resultItems.getRequest().getUrl());
        }
    }
}
