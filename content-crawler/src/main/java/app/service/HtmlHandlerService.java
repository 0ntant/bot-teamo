package app.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;

import java.util.ArrayList;
import java.util.List;

@Service
public class HtmlHandlerService
{
    public List<String> textElementsExtract(Page page)
    {
        List<String> text = new ArrayList<>();
        text.addAll(extractTextFromDiv(page));
        text.addAll(extractTextFromP(page));
        return text;
    }

    private List<String> extractTextFromDiv(Page page)
    {
        return tagExtract(page, "div");
    }

    private List<String> extractTextFromP(Page page)
    {
        return tagExtract(page, "p");
    }

    private List<String> tagExtract(Page page, String tagExtract)
    {
        List<String> text = new ArrayList<>();
        String rawHtml = page.getHtml().get();
        Document document = Jsoup.parse(rawHtml);

        Elements divElements = document.select(tagExtract);
        for (Element div : divElements)
        {
            div.select("br").remove();
            String cleanText = div.text();
            text.add(cleanText);
        }
        return text;
    }
}
