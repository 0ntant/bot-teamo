package app.redqueen.integration.mq.teamoAutoReg;

import integration.dto.reg.RegTeamoUserImgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TeamoAutoRegPublisher
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.producer.teamo-auto-reg.exc}")
    private String tarUserTeamoRegExc;

    @Value("${spring.rabbitmq.producer.teamo-auto-reg.route-key}")
    private String tarUserTeamoRegRoute;

    public void sendRegUserTeamo(RegTeamoUserImgDto regTeamoUserImgDto)
    {
        try
        {
            rabbitTemplate.convertAndSend(
                    tarUserTeamoRegExc,
                    tarUserTeamoRegRoute,
                    regTeamoUserImgDto
            );
            log.info("[PRODUCER] send user name={} email={} gender={} resIndex={} to teamo-auto-reg service",
                    regTeamoUserImgDto.getUserDto().getName(),
                    regTeamoUserImgDto.getUserDto().getEmail(),
                    regTeamoUserImgDto.getUserDto().getGender(),
                    regTeamoUserImgDto.getUserDto().getResPlaceIndex()
            );
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
        }
    }
}
