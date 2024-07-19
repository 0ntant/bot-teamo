package app.provider.integration.proxyscrape.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProxyDto
{
    Boolean alive;
    Float alive_since;
    String anonymity;
    Float average_timeout;
    Float first_seen;
    IpData ip_data;
    Float ip_data_last_update;
    Float last_seen;
    Integer port;
    String protocol;
    String proxy;
    Boolean ssl;
    Float timeout;
    Integer times_alive;
    Integer times_dead;
    Float uptime;
    String ip;
}

