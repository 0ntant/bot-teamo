package auto.reg.integration.mq.proxyProvider;

import auto.reg.service.ProxyService;
import integration.dto.ProxyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProxyProviderConsumer
{
    @Autowired
    ProxyService proxyService;

    @RabbitListener(queues = "${spring.rabbitmq.consumer.proxy-provider.que}")
    @RabbitHandler
    public void receiveProxyDto(ProxyDto proxyDto)
    {
        log.info("[CONSUMER] proxy ip={} port={} isEnable={}",
                proxyDto.getIp(),
                proxyDto.getPort(),
                proxyDto.getEnable()
        );
        proxyService.setProxyValues(proxyDto);
    }
}
