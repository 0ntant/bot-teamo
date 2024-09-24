package integration;

import app.redqueen.integration.GeneralClient;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeneralClientTests
{

    GeneralClient generalClient = new GeneralClient();

    @Test
    void createImg() throws IOException {
        //given
        String fileUrl
                = "https://img04.teamo.ru/v2/fit?height=800&url=http://img04.teamo.ru:8080/y/a/12426442.jpg&width=800&sign=e62aZgPtuEfn%2BY9bcFJ54RBy9fZRtqxrAdQrsflvJAA%3D";
        RestTemplate restTemplate = new RestTemplate();
        generalClient.setClient(restTemplate);
        //then
        Files.write( Path.of("src/test/java/integration/img2.png") , generalClient.getFileData(fileUrl));
        //expected
    }
}
