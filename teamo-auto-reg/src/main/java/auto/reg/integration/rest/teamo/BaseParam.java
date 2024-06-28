package auto.reg.integration.rest.teamo;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class BaseParam
{
    public static MultiValueMap<String, String> getBasePostParameters(String token)
    {
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();

        requestMap.add("app_id", "frontend");
        requestMap.add("locale", "ru");
        requestMap.add("tv", "2");
        requestMap.add("token", token);
        return requestMap;
    }
}
