package app.redqueen.integration.imgGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImgGeneratorClient
{
    RestOperations client;
    String horizontalFlipUrl;

    public byte[] horizontalFlip(byte[] imgData)
    {
        try
        {
            return client.postForObject(horizontalFlipUrl, imgData, byte[].class);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
