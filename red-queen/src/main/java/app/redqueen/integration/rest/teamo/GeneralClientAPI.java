package app.redqueen.integration.rest.teamo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class GeneralClientAPI
{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;
    private ObjectMapper objectMapper ;
    private String token;
    private String locale;
    protected final Logger logger = LoggerFactory.getLogger(GeneralClientAPI.class);

    public GeneralClientAPI(String token)
    {
        restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();
        objectMapper = new ObjectMapper();
        locale =  "ru";
        this.token = token;
    }

    public GeneralClientAPI(String token, String locale)
    {
//        restTemplate = new RestTemplateBuilder()
//                .setConnectTimeout(Duration.ofSeconds(60))
//                .setReadTimeout(Duration.ofSeconds(60))
//                .build();
//        objectMapper = new ObjectMapper();
//        this.token = token;
        this(token);
        this.locale =  locale;
    }

    protected MultiValueMap<String, String> getBasePostParameters()
    {
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<String, String>();

        requestMap.add("app_id", "frontend");
        requestMap.add("locale", locale);
        requestMap.add("tv", "2");
        requestMap.add("token", token);
        return requestMap;
    }

    protected JsonNode postRequestHandler (MultiValueMap<String, String> requestMap, String url)
    {
        JsonNode jsonNodeResponseData;
        HttpEntity<MultiValueMap<String, String>> request;
        try
        {
            request =
                    new HttpEntity<MultiValueMap<String, String>>(requestMap, httpHeaders);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error(ex.getMessage());
            return  null;
        }

        try
        {
            ResponseEntity<String> response =
                    restTemplate
                            .postForEntity(url, request, String.class);
            jsonNodeResponseData = objectMapper.readTree(response.getBody());
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return jsonNodeResponseData;
    }
}
