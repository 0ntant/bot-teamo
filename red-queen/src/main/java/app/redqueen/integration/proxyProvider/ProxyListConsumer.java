package app.redqueen.integration.proxyProvider;

import app.redqueen.service.ProxyService;
import integration.dto.ProxyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProxyListConsumer
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
        proxyService.setProxy(proxyDto);
    }

}
