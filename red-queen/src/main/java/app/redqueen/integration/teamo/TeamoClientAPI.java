package app.redqueen.integration.teamo;

import app.redqueen.integration.teamo.url.TeamoAPI;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.MultiValueMap;

public class TeamoClientAPI extends GeneralClientAPI
        implements TeamoClient
{
    TeamoAPI teamoAPI;

    public TeamoClientAPI(String token) {
        super(token);
        teamoAPI = new TeamoAPI();
    }

    public TeamoClientAPI(String token, String locale) {
        super(token, locale);
    }

    @Override
    public JsonNode getSpecialUsers() {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("with_teaser", "1");

        jsonNode = super.postRequestHandler(requestMap, teamoAPI.getSpecialUsers());

        return jsonNode;
    }

    @Override
    public JsonNode searchPersons(int page, int perPage) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("with_filter", "1");
        requestMap.add("page", String.valueOf(page));
        requestMap.add("per_page", String.valueOf(perPage));

        jsonNode = super.postRequestHandler(requestMap, teamoAPI.getSearchPersons());

        return jsonNode;
    }

    @Override
    public JsonNode getIncome(int page, int perPage) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("with_filter", "1");
        requestMap.add("page", String.valueOf(page));
        requestMap.add("per_page", String.valueOf(perPage));

        jsonNode = super.postRequestHandler(requestMap, teamoAPI.getIncome());

        return jsonNode;
    }

    @Override
    public JsonNode getMutual(int page, int perPage) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("page", String.valueOf(page));
        requestMap.add("per_page", String.valueOf(perPage));

        jsonNode = super.postRequestHandler(requestMap, teamoAPI.getMutual());

        return jsonNode;
    }

    @Override
    public JsonNode getStatistic() {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        jsonNode = super.postRequestHandler(requestMap, teamoAPI.getStatistic());

        return jsonNode;
    }

    @Override
    public JsonNode getGuests(int page, int perPage) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("page", String.valueOf(page));
        requestMap.add("per_page", String.valueOf(perPage));

        jsonNode = super.postRequestHandler(requestMap, teamoAPI.getGuests());

        return jsonNode;
    }

    @Override
    public JsonNode searchPersonsOthers() {
        throw new UnsupportedOperationException();
    }
}
