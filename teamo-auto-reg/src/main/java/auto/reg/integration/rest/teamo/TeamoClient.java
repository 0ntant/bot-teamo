package auto.reg.integration.rest.teamo;

import auto.reg.mapper.RegUserDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import integration.dto.reg.RegTeamoUserDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class TeamoClient
{
    @Value("${teamoAPI.nextStep}")
    String nextStepUrl;
    @Value("${teamoAPI.editMainInfo}")
    String editMainInfoUrl;
    @Value("${teamoAPI.cancelPsychoTesting}")
    String cancelPsychoTestingUrl;
    @Value("${teamoAPI.skipConfirmation}")
    String skipConfirmationUrl;

    @Setter
    String token;

    ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate= new RestTemplate();

    public JsonNode getNextStep() throws JsonProcessingException
    {
        return  objectMapper.readTree
                (
                restTemplate.postForEntity(
                        nextStepUrl,
                        BaseParam.getBasePostParameters(token),
                        String.class).getBody()
                );
    }

    public JsonNode editMainInfo(RegTeamoUserDto regTeamoUserDto) throws JsonProcessingException
    {
        MultiValueMap<String, String> requestParams = BaseParam.getBasePostParameters(token);
        requestParams.addAll(RegUserDtoMapper.getParams(regTeamoUserDto));

        return objectMapper.readTree(
                restTemplate.postForEntity(
                        editMainInfoUrl,
                        requestParams,
                        String.class
                ).getBody());
    }


    public JsonNode cancelPsychoTesting() throws JsonProcessingException
    {
        MultiValueMap<String, String> requestParams = BaseParam.getBasePostParameters(token);
        requestParams.add("step", "1");
        requestParams.add("block", "1");

        return  objectMapper.readTree
                (
                        restTemplate.postForEntity(
                                cancelPsychoTestingUrl,
                                requestParams,
                                String.class).getBody()
                );
    }

    public JsonNode skipConfirmation() throws JsonProcessingException
    {
        return  objectMapper.readTree
                (
                        restTemplate.postForEntity(
                                skipConfirmationUrl,
                                BaseParam.getBasePostParameters(token),
                                String.class).getBody()
                );
    }
}
