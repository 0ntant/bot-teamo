package service.network.IT.apiMock;

import app.redqueen.integration.rest.TeamoClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;

public class TeamoClientFileMock extends GeneralNetMock
        implements TeamoClient
{
    @Override
    public JsonNode getSpecialUsers() {
        throw  new UnsupportedOperationException();
    }

    @Override
    public JsonNode searchPersons(int page, int perPage) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public JsonNode getIncome(int page, int perPage)
    {
        File getIncome = new File(selfFolder.getAbsolutePath().concat("/getIncome"));

        try {
            return objectMapper.readTree(getIncome);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public JsonNode getMutual(int page, int perPage) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public JsonNode getStatistic() {
        File getIncome = new File(selfFolder.getAbsolutePath().concat("/getStatistic"));

        try {
            return objectMapper.readTree(getIncome);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public JsonNode getGuests(int page, int perPage) {
        File getGuests = new File(selfFolder.getAbsolutePath().concat("/getGuests"));

        try {
            return objectMapper.readTree(getGuests);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public JsonNode searchPersonsOthers() {
        throw  new UnsupportedOperationException();
    }
}
