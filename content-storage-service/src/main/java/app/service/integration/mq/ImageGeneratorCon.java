package app.service.integration.mq;


import app.service.config.RabbitConfiguration;
import app.service.service.ImageAvatarService;
import integration.dto.ImgAvaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ImageGeneratorCon
{
    @Autowired
    ImageAvatarService imgAvaServ;

    @RabbitListener(queues = RabbitConfiguration.imgAvaQue)
    @RabbitHandler
    public void receiveImageAvatar(ImgAvaDto imgDto)
    {
        log.info("[CONSUMER] received image avatar {}",imgDto.getName());
        imgAvaServ.saveFromImgGenerator(imgDto);
    }


}



