package app.provider.mapper;

import app.provider.integration.geonode.dto.ProxyDto;
import app.provider.model.Geolocation;
import app.provider.model.ProxyHost;

public class GeoNodeProxyToProxyHost
{
    public static ProxyHost map(ProxyDto proxyDto)
    {
        String protocol =  proxyDto.getProtocols().get(0);
        String proxy = String.format("%s://%s:%s",
                protocol,
                proxyDto.getIp(),
                proxyDto.getPort()
        );
        return ProxyHost.builder()
                .proxy(proxy)
                .protocol(protocol)
                .ip(proxyDto.getIp())
                .port(Integer.valueOf(proxyDto.getPort()))
                .https(false)
                .anonymity(proxyDto.getAnonymityLevel())
                .score(proxyDto.getSpeed())
                .geolocation(
                        new Geolocation(
                                proxyDto.getCountry(),
                                proxyDto.getCity()
                        )
                )
                .build();
    }
}
