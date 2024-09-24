package service.network.IT.apiMock;

import app.redqueen.integration.teamo.FacesClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;

public class FacesClientFileMock extends  GeneralNetMock
        implements FacesClient
{
    @Override
    public JsonNode getMatches(int page, int perPage)
    {
        File getMatches = new File(likesFolder.getAbsolutePath().concat("/getMatches"));
        try
        {
            return objectMapper.readTree(getMatches);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonNode saveMatchVote(int personId)
    {
        File saveMatchVote = new File(likesFolder.getAbsolutePath().concat("/saveMatchVote"));
        try
        {
            return objectMapper.readTree(saveMatchVote);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
