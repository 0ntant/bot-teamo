package app.provider.integration.proxyscrape;

import app.provider.integration.proxyscrape.dto.ProxyDto;
import app.provider.integration.proxyscrape.dto.RecordDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Slf4j
public class ProxyscrapeClient
{
    RestOperations restOperations;
    String proxyListUrl;

    public List<ProxyDto> getProxyDtoList()
    {
        log.info("Getting proxy list pub url={}", proxyListUrl);
        try
        {
            RecordDto proxyData = restOperations.getForObject(
                    proxyListUrl,
                    RecordDto.class
            );

            return proxyData.getProxies();
        }catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
