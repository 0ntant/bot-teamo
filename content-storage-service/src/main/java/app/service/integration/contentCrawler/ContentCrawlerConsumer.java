package app.service.integration.contentCrawler;

import app.service.exception.EntityAlreadyExists;
import app.service.mapper.TextDataMapper;
import app.service.service.TextDataService;
import integration.TextDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentCrawlerConsumer
{
    @Autowired
    TextDataService textDataService;


    @RabbitListener(queues = "${spring.rabbitmq.con.content-crawler.que}")
    @RabbitHandler
    public void receiveTexData(TextDataDto textDataDto)
    {
        log.info("[CONSUMER] receive title {} tags {}",
                textDataDto.textContent(),
                textDataDto.dataTags()
        );
        try
        {
            textDataService.save(TextDataMapper.map(textDataDto));
        }
        catch (EntityAlreadyExists ex)
        {
            log.warn("TextData already exists, skip");
        }
    }
}
