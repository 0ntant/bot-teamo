package app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

@Service
@RequiredArgsConstructor
public class CrawlerJokeService
{
    final Spider spider;

    public void run(String startUrl)
    {
        spider.addUrl(startUrl);
        spider.stopWhenComplete();
        spider.run();
    }

    public void stop()
    {
        spider.stop();
    }
}
