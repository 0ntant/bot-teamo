package app.provider.integration.proxyscrape.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecordDto
{
    Integer shown_records;
    Integer total_records;
    Integer skip;
    Boolean nextpage;
    List<ProxyDto> proxies;
}
