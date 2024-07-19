package app.provider.mapper;

import app.provider.integration.proxyscrape.dto.ProxyDto;
import app.provider.model.Geolocation;
import app.provider.model.ProxyHost;

public class ProxyscrapeProxyToProxyHost
{
    public static ProxyHost map(ProxyDto proxyDto)
    {
        return ProxyHost.builder()
                .proxy(proxyDto.getProxy())
                .protocol(proxyDto.getProtocol())
                .ip(proxyDto.getIp())
                .port(proxyDto.getPort())
                .https(proxyDto.getSsl())
                .anonymity(proxyDto.getAnonymity())
                .score(1)
                .geolocation(
                        new Geolocation(
                                proxyDto.getIp_data().getCountry(),
                                proxyDto.getIp_data().getCity()
                        )
                )
                .build();
    }
}
