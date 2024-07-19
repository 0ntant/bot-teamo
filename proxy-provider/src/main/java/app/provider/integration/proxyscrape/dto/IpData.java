package app.provider.integration.proxyscrape.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IpData
{
    String as;
    String asname;
    String city;
    String continent;
    String continentCode;
    String country;
    String countryCode;
    String district;
    Boolean hosting;
    String isp;
    Float lat;
    Float lon;
    Boolean mobile;
    String org;
    Boolean proxy;
    String regionName;
    String status;
    String timezone;
    String zip;
}
