package img.gen.integration.contentStorageService;

import integration.dto.ImgAvaDto.ImgAvaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentStoragePub
{
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.pub.css.exc}")
    private String cssImgAvaExc;

    @Value("${spring.rabbitmq.pub.css.route-key}")
    private String cssImgAvaRoute;

    public void sendMsq(ImgAvaDto imgAvaDto)
    {
        try
        {
            rabbitTemplate.convertAndSend(
                    cssImgAvaExc,
                    cssImgAvaRoute,
                    imgAvaDto
            );
            log.info("[PRODUCER] send img gender img {}",
                    imgAvaDto.getName()
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
