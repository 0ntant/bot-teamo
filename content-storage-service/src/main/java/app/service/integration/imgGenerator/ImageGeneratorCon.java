package app.service.integration.imgGenerator;


import app.service.service.ImageAvatarService;
import integration.dto.ImgAvaDto.ImgAvaDto;
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

    @RabbitListener(queues = "${spring.rabbitmq.con.img-gen.que}")
    @RabbitHandler
    public void receiveImageAvatar(ImgAvaDto imgDto)
    {
        log.info("[CONSUMER] receive {} image avatar {}",
                imgDto.getOperation(),
                imgDto.getName()
        );
        imgAvaServ.saveFromImgGenerator(imgDto);
    }
}



