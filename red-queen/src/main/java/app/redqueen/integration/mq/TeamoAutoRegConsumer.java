package app.redqueen.integration.mq;


import app.redqueen.dto.input.BotDto;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.ResultOrError;
import app.redqueen.service.network.UserNetServiceFactory;
import app.redqueen.service.network.UserTeamoNetworkService;
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
    private UserTeamoService userTeamoService;
    @Autowired
    private UserNetServiceFactory userNetServiceFactory;

    @RabbitListener(queues = "${spring.rabbitmq.consumer.teamo-auto-reg.que}")
    @RabbitHandler
    public void receiveUserTeamoDto(UserTeamoDto userTeamoDto)
    {
        log.info("[CONSUMER] receive user id={}", userTeamoDto.getUserId());
        if(userTeamoService.isExistById(userTeamoDto.getUserId()))
        {
            log.warn("User id={} already exist", userTeamoDto.getUserId());
            return;
        }

        UserTeamo userTeamo = new UserTeamo();

        userTeamo.setId(userTeamoDto.getUserId());
        userTeamo.setToken(userTeamoDto.getToken());
        userTeamo.setEmail(userTeamoDto.getEmail());
        userTeamo.setPassword(userTeamoDto.getPassword());
        userTeamo.setSysCreateDate(new Date());

        UserTeamoNetworkService userTeamoNetworkService
                = userNetServiceFactory.createUserNetDecorator(userTeamo);

        ResultOrError<UserTeamo> resultOrError = userTeamoNetworkService.getSelfFullInfo();
        if (resultOrError.isErrorResponse())
        {
            log.error("Error self info code={} reason={}",
                    resultOrError.getBlock().getTeamoCode(),
                    resultOrError.getBlock().getReason());
        }
    }
}
