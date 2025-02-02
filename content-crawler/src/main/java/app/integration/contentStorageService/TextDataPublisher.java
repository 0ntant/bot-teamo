package app.integration.contentStorageService;

import integration.TextDataDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TextDataPublisher
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.producer.content-storage-service.exc}")
    private String contentStorageServiceExc;

    @Value("${spring.rabbitmq.producer.content-storage-service.route-key}")
    private String contentStorageServiceRoute;

    public void sendRegUserTeamo(TextDataDto textDataDto)
    {
        try
        {
            rabbitTemplate.convertAndSend(
                    contentStorageServiceExc,
                    contentStorageServiceRoute,
                    textDataDto
            );
            log.info(
                    "[PRODUCER] send textDataDto to content storage service tags={}",
                    textDataDto.dataTags()
            );
        }
        catch (AmqpException ex)
        {
            log.error(ex.getMessage());
        }
    }
}
