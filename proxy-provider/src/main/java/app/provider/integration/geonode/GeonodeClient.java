package app.provider.integration.geonode;

import app.provider.integration.geonode.dto.DataDto;
import app.provider.integration.geonode.dto.ProxyDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Slf4j
public class GeonodeClient
{
    RestOperations restOperations;
    String proxyListUrl;

    public List<ProxyDto> getProxyDtoList()
    {
        log.info("Getting proxy list pub url={}", proxyListUrl);
        Map<String , String > params = new HashMap<>();
        params.put("limit", "100");
        params.put("page", "1");
        params.put("sortBy", "lastChecked");
        params.put("sortType", "desc");
        try
        {
            DataDto proxyData = restOperations.getForObject(
                    proxyListUrl,
                    DataDto.class,
                    params
            );

           return proxyData.getData();
        }catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
