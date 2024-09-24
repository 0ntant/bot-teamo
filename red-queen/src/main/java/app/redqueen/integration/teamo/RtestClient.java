package app.redqueen.integration.teamo;

import app.redqueen.dto.integration.output.LocationDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@AllArgsConstructor
@Component
@Builder
@Slf4j
public class RtestClient
{
    String getLocationUrl;
    RestOperations restOperations;
    ObjectMapper objectMapper;

    public JsonNode getLocationJsons(LocationDto request)
    {
        try
        {
            ResponseEntity<String> response = restOperations.postForEntity(getLocationUrl, request, String.class);
            return objectMapper.readTree(response.getBody());
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
