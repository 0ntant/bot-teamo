package integration.IT;

import auto.reg.TeamoAutoReg;
import auto.reg.maker.ImageUploader;
import auto.reg.util.Base64Util;
import integration.dto.reg.ImageAvaDto;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest(classes = TeamoAutoReg.class)
public class ImageUploaderIT
{
    @Autowired
    ImageUploader imageUploader;

    @Test
    void uploadImg_success() throws IOException
    {
        //given
        String imgName = "img2.png";
        Cookie teamoCookie = new Cookie("teamo", "bbb5f68935066023a4bcdcc8af9e711e:1de5cc177ebefce3c650ad23128b56ef89f7d44b");
        Cookie rememberCookie = new Cookie("teamo_remember_v2", "a9bbc6b645b816fb22958782");

        //then
        imageUploader.uploadAvaImg(
                List.of(teamoCookie, rememberCookie),
                createImgDto(imgName)
        );
        //expected
    }

    private ImageAvaDto createImgDto(String imgName) throws IOException
    {
        String photoImgPath = "src/test/resources/photos/";
        ImageAvaDto imageAvaDto = new ImageAvaDto();
        imageAvaDto.setName(imgName);
        imageAvaDto.setBase64(
                new String(Base64Util.encode(
                        Files.readAllBytes
                                (Path.of(photoImgPath+imgName)))));
        return imageAvaDto;
    }
}
