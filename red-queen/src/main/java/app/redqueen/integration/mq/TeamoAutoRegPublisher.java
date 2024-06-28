package app.redqueen.integration.mq;


import integration.dto.reg.RegTeamoUserDto;
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

    public void sendRegUserTeamo(RegTeamoUserDto regTeamoUserDto)
    {
        try
        {
            rabbitTemplate.convertAndSend(
                    tarUserTeamoRegExc,
                    tarUserTeamoRegRoute,
                    regTeamoUserDto
            );
            log.info("[PRODUCER] send user name={} email={} gender={} resIndex={} to teamo-auto-reg service",
                    regTeamoUserDto.getName(),
                    regTeamoUserDto.getEmail(),
                    regTeamoUserDto.getGender(),
                    regTeamoUserDto.getResPlaceIndex()
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
