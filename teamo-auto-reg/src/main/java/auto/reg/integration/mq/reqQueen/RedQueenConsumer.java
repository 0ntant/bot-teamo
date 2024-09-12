package auto.reg.integration.mq.reqQueen;


import auto.reg.service.UsersOrderService;
import integration.dto.reg.RegTeamoUserImgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedQueenConsumer
{
    @Autowired
    UsersOrderService usersOrderService;

    @RabbitListener(queues = "${spring.rabbitmq.consumer.red-queen.que}")
    @RabbitHandler
    public void receiveUserTeamoImgDto(RegTeamoUserImgDto regTeamoUserDto)
    {
        log.info("[CONSUMER] receive user to reg name={} email={} gender={}",
                regTeamoUserDto.getUserDto().getEmail(),
                regTeamoUserDto.getUserDto().getName(),
                regTeamoUserDto.getUserDto().getGender()
        );
        usersOrderService.addUserToQue(regTeamoUserDto);
    }
}
