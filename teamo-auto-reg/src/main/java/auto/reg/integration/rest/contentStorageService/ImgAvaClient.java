package auto.reg.integration.rest.contentStorageService;

import auto.reg.mapper.ImgAvaDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import integration.dto.ImgAvaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ImgAvaClient
{
    private static final Logger log = LoggerFactory.getLogger(ImgAvaClient.class);
    @Value("${content-storage-serviceAPI.countByGender}")
    String countByGenderUrl;
    @Value("${content-storage-serviceAPI.infoBase64ByGender}")
    String infoBase64ByGenderUrl;

    ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();

    public int getCountByGender(String gender)
    {
        return Integer.valueOf(restTemplate.getForObject(
                String.format("%s?gender={gender}", countByGenderUrl),
                String.class,
                gender
        ));
    }

    public ImgAvaDto getImgAvaDtoByGender(String gender)
    {
       ImgAvaDto imgAvaDto = new ImgAvaDto();
       try
       {
           imgAvaDto = ImgAvaDtoMapper.result(objectMapper.readTree(
                   restTemplate.getForObject(String.format(
                           "%s?gender={gender}",
                           infoBase64ByGenderUrl), String.class, gender)
           ));
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }
       return imgAvaDto;
    }
}
