package app.redqueen.integration.rest;

import com.fasterxml.jackson.databind.JsonNode;

public interface FacesClient
{
    JsonNode getMatches(int page, int perPage);
    JsonNode saveMatchVote(int personId);
}
