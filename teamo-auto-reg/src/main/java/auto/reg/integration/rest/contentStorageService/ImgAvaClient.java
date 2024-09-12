package auto.reg.integration.rest.contentStorageService;

import auto.reg.mapper.ImgAvaDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import integration.dto.ImgAvaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImgAvaClient
{
    String countByGenderUrl;
    String infoBase64ByGenderUrl;

    ObjectMapper objectMapper;
    RestTemplate restTemplate;

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
