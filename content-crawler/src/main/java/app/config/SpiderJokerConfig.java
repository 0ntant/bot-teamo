package app.config;

import app.service.JokePipeline;
import app.service.JokerProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

@Configuration
public class SpiderJokerConfig
{
    @Value("${proxy.http.port}")
    Integer proxyPort;
    @Value("${proxy.http.host}")
    String proxyHost;

    @Autowired
    JokerProcessor jokerProcessor;
    @Autowired
    JokePipeline jokePipeline;

    @Bean
    public Spider spider()
    {
        return Spider.create(jokerProcessor)
                .setDownloader(httpClientDownloader())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new HashSetDuplicateRemover()))
                .addPipeline(jokePipeline)
                .thread(5);
    }

    private HttpClientDownloader httpClientDownloader()
    {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(
                SimpleProxyProvider.from(
                        new Proxy(
                                proxyHost,
                                proxyPort
                        )
                )
        );
        return httpClientDownloader;
    }
}
