package auto.reg.integration.mq;

import integration.dto.UserTeamoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedQueenPublisher
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.producer.red-queen.exc}")
    private String rqUserTeamoExc;

    @Value("${spring.rabbitmq.producer.red-queen.route-key}")
    private String rqUserTeamoRoute;

    public void sendUserTeamo(UserTeamoDto userTeamoDto)
    {
        try
        {
            rabbitTemplate.convertAndSend(
                    rqUserTeamoExc,
                    rqUserTeamoRoute,
                    userTeamoDto
            );
            log.info("[PUBLISHER] send user id={} email={}",
                    userTeamoDto.getUserId(),
                    userTeamoDto.getEmail()
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
