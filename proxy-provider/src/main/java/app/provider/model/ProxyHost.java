package app.provider.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProxyHost
{
    String proxy;
    String protocol;
    String ip;
    Integer port;
    Boolean https;
    String anonymity;
    Integer score;
    Geolocation geolocation;
}
