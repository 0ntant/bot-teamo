package service;

import img.gen.service.ImgProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
public class ImgProcessingServiceMock
{
    ImgProcessingService imgProcessingService = new ImgProcessingService("src/main/resources/images/temp");

    @Test
    public void flippingImg_saveImg() throws Exception {
        //given
        File imgToFlip = new File("src/test/java/service/imgInstance.jpg");
        File flippedImg = new File("src/test/java/service/imgFliped.jpg");
        if (flippedImg.exists())
        {
           Files.delete(Path.of(flippedImg.getAbsolutePath()));
        }
        //then
        byte [] imgDataToFlip = Files.readAllBytes(
                Path.of(imgToFlip.getAbsolutePath()
                )
        );
        byte[] flippedImgData = imgProcessingService.horizontalFlip(imgDataToFlip);
        Files.write(Path.of(flippedImg.getAbsolutePath()), flippedImgData);
        //expected
        assertNotEquals(flippedImgData, imgDataToFlip);
        assertTrue(imgToFlip.exists());
    }

    @Test
    public void cutUpper_saveImg() throws Exception {
        //given
        File imgToFlip = new File("src/test/java/service/imgInstance.jpg");
        File cutUpper = new File("src/test/java/service/cutUpper.jpg");
        if (cutUpper.exists())
        {
            Files.delete(Path.of(cutUpper.getAbsolutePath()));
        }
        //then
        byte [] imgDataToFlip = Files.readAllBytes(
                Path.of(imgToFlip.getAbsolutePath()
                )
        );
        byte[] flippedImgData = imgProcessingService.cutUpper(imgDataToFlip, 50);
        Files.write(Path.of(cutUpper.getAbsolutePath()), flippedImgData);
        //expected
        assertNotEquals(flippedImgData, imgDataToFlip);
        assertTrue(imgToFlip.exists());
    }

    @Test
    public void cutBottom_saveImg() throws Exception {
        //given
        File imgToFlip = new File("src/test/java/service/imgInstance.jpg");
        File cutBottom = new File("src/test/java/service/cutBottom.jpg");
        if (cutBottom.exists())
        {
            Files.delete(Path.of(cutBottom.getAbsolutePath()));
        }
        //then
        byte [] imgDataToFlip = Files.readAllBytes(
                Path.of(imgToFlip.getAbsolutePath()
                )
        );
        byte[] flippedImgData = imgProcessingService.cutBottom(imgDataToFlip, 50);
        Files.write(Path.of(cutBottom.getAbsolutePath()), flippedImgData);
        //expected
        assertNotEquals(flippedImgData, imgDataToFlip);
        assertTrue(imgToFlip.exists());
    }

    @Test
    public void cutLeft_saveImg() throws Exception {
        //given
        File imgToFlip = new File("src/test/java/service/imgInstance.jpg");
        File cutLeft = new File("src/test/java/service/cutLeft.jpg");
        if (cutLeft.exists())
        {
            Files.delete(Path.of(cutLeft.getAbsolutePath()));
        }
        //then
        byte [] imgDataToFlip = Files.readAllBytes(
                Path.of(imgToFlip.getAbsolutePath()
                )
        );
        byte[] flippedImgData = imgProcessingService.cutLeft(imgDataToFlip, 50);
        Files.write(Path.of(cutLeft.getAbsolutePath()), flippedImgData);
        //expected
        assertNotEquals(flippedImgData, imgDataToFlip);
        assertTrue(imgToFlip.exists());
    }

    @Test
    public void cutRight_saveImg() throws Exception
    {
        //given
        File imgToFlip = new File("src/test/java/service/imgInstance.jpg");
        File cutRight = new File("src/test/java/service/cutRight.jpg");
        if (cutRight.exists())
        {
            Files.delete(Path.of(cutRight.getAbsolutePath()));
        }

        //then
        byte [] imgDataToFlip = Files.readAllBytes(
                Path.of(imgToFlip.getAbsolutePath()
                )
        );
        byte[] flippedImgData = imgProcessingService.cutRight(imgDataToFlip, 50);
        Files.write(Path.of(cutRight.getAbsolutePath()), flippedImgData);

        //expected
        assertNotEquals(flippedImgData, imgDataToFlip);
        assertTrue(imgToFlip.exists());
    }
}
