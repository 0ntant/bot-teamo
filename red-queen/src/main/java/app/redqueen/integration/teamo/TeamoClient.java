package app.redqueen.integration.teamo;

import com.fasterxml.jackson.databind.JsonNode;

public interface TeamoClient
{
    JsonNode getSpecialUsers();

    JsonNode searchPersons(int page, int perPage);

    JsonNode getIncome(int page, int perPage);

    JsonNode getMutual(int page, int perPage);

    JsonNode getStatistic();

    JsonNode getGuests(int page, int perPage);

    JsonNode searchPersonsOthers();
}
