package service;

import img.gen.ImgGenerator;
import img.gen.service.ImgServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ImgGenerator.class)
public class ImgProvideServiceImplIT
{
    @Autowired
    ImgServiceImpl imgServiceImpl;

    @Test
    void regImage_CSSReturnTrue() throws IOException
    {
        //given
       String imgToSend  = "src/test/java/service/imgInstance.jpg";

       //then
       imgServiceImpl.regToCSS(
               Files.readAllBytes(Path.of(imgToSend)),
               "imgInstance.jpg"
       );

        //expected
       assertTrue(
               imgServiceImpl.isReg(
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
        imgServiceImpl.createImg();
    }
}
