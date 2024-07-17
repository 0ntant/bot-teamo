package app.provider.integration.mq;

import integration.dto.ProxyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProxyListPublisher
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.producer.proxy-pub.exc}")
    private String rqProxyExc;

    @Value("${spring.rabbitmq.producer.proxy-pub.route-key}")
    private String rqProxyRoute;

    public void sendProxyDto(ProxyDto proxyDto)
    {
        try
        {
            rabbitTemplate.convertAndSend(
                    rqProxyExc,
                    rqProxyRoute,
                    proxyDto
            );
            log.info("[PRODUCER] send proxy ip={} port={} isEnable={} to proxy list",
                proxyDto.getIp(),
                proxyDto.getPort(),
                proxyDto.getEnable()
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
