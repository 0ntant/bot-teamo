package service.network.IT.apiMock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

abstract public class GeneralNetMock
{
    protected File usersFolder = new File("src/test/resources/apiTeamoResponses/users");
    protected File selfFolder = new File("src/test/resources/apiTeamoResponses/selfInfo");
    protected File messagesFolder = new File("src/test/resources/apiTeamoResponses/messages");
    protected File likesFolder = new File("src/test/resources/apiTeamoResponses/likes");
    protected File blockResponseDir = new File("src/test/resources/apiTeamoResponses/api/blockAnswers");
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected File[] getUserFiles()
    {
        return usersFolder.listFiles();

    }

    protected JsonNode getBlockResponse(String requestFileName) throws IOException
    {
        File[]  blockFiles = blockResponseDir.listFiles();
        for (File blockFile : blockFiles)
        {
            if (blockFile.getName().equals(requestFileName))
                return objectMapper.readTree(blockFile);
        }

        throw new NoSuchElementException(String.format("No such mock block file user in directory %s",
                blockResponseDir.getAbsolutePath()));
    }
}
