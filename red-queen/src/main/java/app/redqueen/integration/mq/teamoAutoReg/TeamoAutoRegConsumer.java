package app.redqueen.integration.mq.teamoAutoReg;


import app.redqueen.model.UserTeamo;
import app.redqueen.service.BotFullInfoGetterService;
import integration.dto.UserTeamoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TeamoAutoRegConsumer
{
    @Autowired
    BotFullInfoGetterService botFullInfoGetterService;

    @RabbitListener(queues = "${spring.rabbitmq.consumer.teamo-auto-reg.que}")
    @RabbitHandler
    public void receiveUserTeamoDto(UserTeamoDto userTeamoDto)
    {
        log.info("[CONSUMER] receive user id={}", userTeamoDto.getUserId());

        UserTeamo userTeamo = new UserTeamo();

        userTeamo.setId(userTeamoDto.getUserId());
        userTeamo.setToken(userTeamoDto.getToken());
        userTeamo.setEmail(userTeamoDto.getEmail());
        userTeamo.setPassword(userTeamoDto.getPassword());
        userTeamo.setSysCreateDate(new Date());

        botFullInfoGetterService.getFullInfoUser(userTeamo);
    }
}
