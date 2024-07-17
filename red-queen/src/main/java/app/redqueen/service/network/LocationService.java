package app.redqueen.service.network;

import app.redqueen.dto.integration.output.LocationDto;
import app.redqueen.integration.rest.teamo.RtestClient;
import app.redqueen.mapper.api.JsonNodeToCityIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService
{
    @Autowired
    RtestClient rtestClient;

    public List<Integer> getLocationIndexes(String cityName)
    {
        return JsonNodeToCityIndex.map(
                rtestClient.getLocationJsons(
                        defaultRequest(cityName)
                )
        );
    }

    private LocationDto defaultRequest(String cityName)
    {
        return new LocationDto("frontend" , "ru",2 , cityName);
    }
}
