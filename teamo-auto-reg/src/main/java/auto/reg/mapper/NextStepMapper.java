package auto.reg.mapper;


import com.fasterxml.jackson.databind.JsonNode;

public class NextStepMapper
{
    public static String result(JsonNode responseJson)
    {
        return responseJson.path("result").path("step").asText();
    }

    public static boolean isPassed(JsonNode responseJson)
    {
        return responseJson.path("result").path("is_all_registration_steps_passed").asBoolean();
    }
}
