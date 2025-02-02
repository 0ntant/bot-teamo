package app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JokerProcessor implements PageProcessor
{
    final HtmlHandlerService htmlHandlerService;

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setTimeOut(10000);

    @Override
    public void process(Page page)
    {
        List<String> divTexts = page.getHtml().xpath("//div//text()").all();
        divTexts.addAll(htmlHandlerService.textElementsExtract(page));

        page.addTargetRequests(page.getHtml().links().all());

        page.putField("text", divTexts);
    }
}
