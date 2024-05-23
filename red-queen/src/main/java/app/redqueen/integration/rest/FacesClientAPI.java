package app.redqueen.integration.rest;

import app.redqueen.integration.rest.url.FacesAPI;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.MultiValueMap;

public class FacesClientAPI extends GeneralClientAPI
        implements FacesClient
{
    FacesAPI facesAPI;
    public FacesClientAPI(String token) {
        super(token);
        facesAPI = new FacesAPI();
    }

    public FacesClientAPI(String token, String locale) {
        super(token, locale);
    }

    @Override
    public JsonNode getMatches(int page, int perPage) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("type", "all");
        requestMap.add("page", String.valueOf(page));
        requestMap.add("per_page", String.valueOf(perPage));

        jsonNode = super.postRequestHandler(requestMap, facesAPI.getMatches());

        return jsonNode;
    }

    @Override
    public JsonNode saveMatchVote(int personId)
    {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("person_id", String.valueOf(personId));
        requestMap.add("vote", "1");

        jsonNode = super.postRequestHandler(requestMap, facesAPI.getSaveMatchVote());
        return jsonNode;
    }
}
