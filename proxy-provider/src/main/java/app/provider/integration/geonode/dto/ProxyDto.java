package app.provider.integration.geonode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProxyDto
{
    String _id;
    String ip;
    String anonymityLevel;
    String asn;
    String city;
    String country;
    String create_at;
    Long lastChecked;
    Float latency;
    String org;
    String port;
    List<String> protocols;
    String region;
    Integer responseTime;
    Integer speed;
    String updated_at;
    Float upTime;
    Integer upTimeSuccessCount;
    Integer upTimeTryCount;
}
