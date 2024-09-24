package app.redqueen.service.network;

import app.redqueen.integration.imgGenerator.ImgGeneratorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgGeneratorService
{
    @Autowired
    ImgGeneratorClient imgGeneratorClient;

    public byte[] horizontalFlip(byte[] img)
    {
        return imgGeneratorClient.horizontalFlip(img);
    }
}
