package service;

import img.gen.ImgGenerator;
import img.gen.service.ImgService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ImgGenerator.class)
public class ImgServiceIT
{
    @Autowired
    ImgService imgService;

    @Test
    void regImage_CSSReturnTrue() throws IOException
    {
        //given
       String imgToSend  = "src/test/java/service/imgInstance.jpg";

       //then
       imgService.regToCSS(
               Files.readAllBytes(Path.of(imgToSend)),
               "imgInstance.jpg"
       );

        //expected
       assertTrue(
               imgService.isReg(
                        Files.readAllBytes(
                                Path.of(imgToSend)
                        )
               )
       );
    }

    @Test
    void getCurrImages()
    {
        //
        imgService.createImg();
    }
}
