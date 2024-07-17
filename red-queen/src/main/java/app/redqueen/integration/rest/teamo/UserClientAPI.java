package app.redqueen.integration.rest.teamo;

import app.redqueen.integration.rest.teamo.url.UserAPI;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.MultiValueMap;

public class UserClientAPI extends GeneralClientAPI
        implements UserClient
{
    private UserAPI userAPI;

    public UserClientAPI(String token) {
        super(token);
        userAPI = new UserAPI();
    }

    public UserClientAPI(String token, String locale) {
        super(token, locale);
    }

    @Override
    public JsonNode getUserInfoById(long userId) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("user_id", String.valueOf(userId));
        requestMap.add("additional[]", "visit_person");
        requestMap.add("additional[]", "general_v2");
        requestMap.add("additional[]", "photos_v2");
        requestMap.add("additional[]", "lifestyle");
        requestMap.add("additional[]", "likes");
        requestMap.add("additional[]", "dislikes");
        requestMap.add("additional[]", "looking_for");
        requestMap.add("additional[]", "locked_fields");
        requestMap.add("additional[]", "extended_compatibility");
        requestMap.add("additional[]", "credits");
        requestMap.add("additional[]", "config");

        jsonNode = super.postRequestHandler(requestMap, userAPI.getProfile());

        return jsonNode;
    }

    @Override
    public JsonNode getUserInfoSelf() {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("additional[]", "visit_person");
        requestMap.add("additional[]", "general_v2");
        requestMap.add("additional[]", "photos_v2");
        requestMap.add("additional[]", "lifestyle");
        requestMap.add("additional[]", "likes");
        requestMap.add("additional[]", "dislikes");
        requestMap.add("additional[]", "looking_for");
        requestMap.add("additional[]", "locked_fields");
        requestMap.add("additional[]", "extended_compatibility");
        requestMap.add("additional[]", "credits");
        requestMap.add("additional[]", "config");

        jsonNode = super.postRequestHandler(requestMap, userAPI.getProfile());

        return jsonNode;
    }

    @Override
    public JsonNode getMessagesByUser(long  page, long perPage) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("type", "all");
        requestMap.add("page", String.valueOf(page));
        requestMap.add("per_page", String.valueOf(perPage));

        jsonNode = super.postRequestHandler(requestMap, userAPI.getMessagesByUser());

        return jsonNode;
    }

    @Override
    public JsonNode getShardableMessages(long personId, long limit, long offset) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("person_id", String.valueOf(personId));
        requestMap.add("limit", String.valueOf(limit));
        requestMap.add("offset", String.valueOf(offset));
        requestMap.add("reverse", "1");

        jsonNode = super.postRequestHandler(requestMap, userAPI.getShardableMessages());

        return jsonNode;
    }

    @Override
    public JsonNode sendMessage(long userId, String message) {
        JsonNode jsonNode;
        MultiValueMap<String, String> requestMap = getBasePostParameters();

        requestMap.add("user_id", String.valueOf(userId));
        requestMap.add("message", message);
        requestMap.add("version_logic", "3");

        jsonNode = super.postRequestHandler(requestMap, userAPI.getSendMessage());

        return jsonNode;
    }
}
