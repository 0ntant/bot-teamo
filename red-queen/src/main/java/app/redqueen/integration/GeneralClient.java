package app.redqueen.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralClient
{
    @Setter
    RestOperations client;

    public byte[] getFileData(String url)
    {
        try
        {
            return client.getForObject(url, byte[].class);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
