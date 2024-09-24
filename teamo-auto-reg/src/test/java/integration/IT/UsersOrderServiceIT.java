package integration.IT;


import auto.reg.Main;
import auto.reg.maker.DriverFactory;
import auto.reg.service.UsersOrderService;
import auto.reg.util.Base64Util;
import integration.dto.reg.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

@SpringBootTest(classes = Main.class)
public class UsersOrderServiceIT
{

    @Autowired
    UsersOrderService usersOrderService;

    @Autowired
    DriverFactory driverFactory;

    @Test
    void localCreateWebDriver()
    {
        WebDriver driver = driverFactory.create();
        driver.get("https://yandex.ru");
        driver.quit();
    }

    @Test
    void try_toRegUser() throws Exception
    {
        //given
        Random rand = new Random();
        RegTeamoUserDto userData = RegTeamoUserDto.builder()
                .name("Саша")
                .birth(new BirthDto(
                        12,
                        5,
                        1998
                ))
                .gender("1")
                .email("mailTest0x08@mail.ru")
                .password("test_pass111")
                .resPlaceIndex(2085)
                .ageSeek(new AgeSeek(
                        18, 40
                ))
                .education("1")
                .activity(String.valueOf(rand.nextInt(1, 10)))
                .income("1")
                .maritalStatus("1")
                .children("1")
                .religion("8")
                .height(String.valueOf(185))
                .smoking("1")
                .alcohol("1")
                .missNextOptStep("0")
                .build();
        RegTeamoUserImgDto userToReg = new RegTeamoUserImgDto();
        userToReg.setUserDto(userData);
        userToReg.setCreateSource("TestCreateSource");
        userToReg.setImageDto(createImgDto("img1.png"));
        //then
        usersOrderService.regUser(userToReg);
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
