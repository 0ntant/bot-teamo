package app.provider.service;

import app.provider.integration.geonode.GeonodeClient;
import app.provider.mapper.GeoNodeProxyToProxyHost;
import app.provider.model.ProxyHost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@Slf4j
public class GeonodeService
{
    @Autowired
    GeonodeClient geonodeClient;
    @Autowired
    ProxyValidateService proxyValidateServ;

    public void addProxyList()
    {
        proxyValidateServ.addToActuatorList(getProxyHostsList());
    }

    public Queue<ProxyHost> getProxyHostsList()
    {

        List<ProxyHost> proxyHostList =
                geonodeClient.getProxyDtoList().stream()
                        .map(GeoNodeProxyToProxyHost::map)
                        .toList();
        log.info("Proxy hosts size={} geonode", proxyHostList.size());
        return  new LinkedList<>(proxyHostList);
    }
}

