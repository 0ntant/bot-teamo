package app.provider.service;

import app.provider.integration.proxyscrape.ProxyscrapeClient;
import app.provider.mapper.GeoNodeProxyToProxyHost;
import app.provider.mapper.ProxyscrapeProxyToProxyHost;
import app.provider.model.ProxyHost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@Slf4j
public class ProxyscrapeService
{
    @Autowired
    ProxyscrapeClient proxyscrapeClient;

    @Autowired
    ProxyValidateService proxyValidateServ;

    public void addProxyList()
    {
        proxyValidateServ.addToActuatorList(getProxyHostsList());
    }

    public Queue<ProxyHost> getProxyHostsList()
    {
        List<ProxyHost> proxyHostList =
                proxyscrapeClient.getProxyDtoList().stream()
                        .map(ProxyscrapeProxyToProxyHost::map)
                        .toList();
        log.info("Proxy hosts size={} proxyscrape", proxyHostList.size());
        return  new LinkedList<>(proxyHostList);
    }
}
