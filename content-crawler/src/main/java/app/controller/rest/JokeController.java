package app.controller.rest;

import app.dto.input.JokeDto;
import app.service.CrawlerJokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "joke")
@RequiredArgsConstructor
public class JokeController
{
    @Autowired
    final CrawlerJokeService crawlerJokeService;

    @Async
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create/task")
    public void createTask(@RequestBody JokeDto jokeDto)
    {
        crawlerJokeService.run(jokeDto.url());
    }

    @PostMapping("stop")
    public void stopCrawler(@RequestBody JokeDto jokeDto)
    {
        crawlerJokeService.stop();
    }
}
