package app.redqueen.dto.integration.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDto
{
    String app_id;
    String locale;
    int tv;
    String location_prefix;
}
