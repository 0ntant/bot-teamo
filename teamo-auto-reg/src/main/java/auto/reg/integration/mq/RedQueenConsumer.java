package auto.reg.integration.mq;


import auto.reg.service.UsersToOrderQueueService;
import integration.dto.reg.RegTeamoUserDto;
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
    UsersToOrderQueueService usersToOrderQueueService;

    @RabbitListener(queues = "${spring.rabbitmq.consumer.red-queen.que}")
    @RabbitHandler
    public void receiveUserTeamoDto(RegTeamoUserDto regTeamoUserDto)
    {
        log.info("[CONSUMER] receive user to reg name={} email={} gender={}",
                regTeamoUserDto.getEmail(),
                regTeamoUserDto.getName(),
                regTeamoUserDto.getGender()
        );
        usersToOrderQueueService.addUserToQue(regTeamoUserDto);
    }
}
