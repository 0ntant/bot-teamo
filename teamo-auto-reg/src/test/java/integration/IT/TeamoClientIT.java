package integration.IT;

import auto.reg.Main;
import auto.reg.integration.rest.teamo.TeamoClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import integration.dto.reg.AgeSeek;
import integration.dto.reg.BirthDto;
import integration.dto.reg.RegTeamoUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest(classes = Main.class)
public class TeamoClientIT
{
    @Autowired
    TeamoClient teamoClient;

    @Test
    void editMainInfo_get200Ok() throws JsonProcessingException {
        //given
        RegTeamoUserDto userData = RegTeamoUserDto.builder()
                .name("Женя")
                .birth(new BirthDto(
                        2,
                        11,
                        1999
                ))
                .gender("1")
                .email("mailTOreg11Test@mail.ru")
                .password("test_pass")
                .resPlaceIndex(2085)
                .ageSeek(new AgeSeek(
                        18, 40
                ))
                .education("1")
                .activity(String.valueOf(new Random().nextInt(1, 10)))
                .income("1")
                .maritalStatus("1")
                .children("1")
                .religion("8")
                .height(String.valueOf(185))
                .smoking("1")
                .alcohol("1")
                .missNextOptStep("0")
                .build();
        teamoClient.setToken("a9bbc6b645b816fb22958782");
        //then
        teamoClient.editMainInfo(userData);
        //expected
    }

    @Test
    void cancelPsychoTesting_get200Ok() throws JsonProcessingException
    {
        //given
        teamoClient.setToken("a9bbc6b645b816fb22958782");
        //then
        teamoClient.cancelPsychoTesting();
        //expected
    }
}
