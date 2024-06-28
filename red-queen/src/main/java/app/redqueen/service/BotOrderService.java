package app.redqueen.service;

import app.redqueen.integration.mq.TeamoAutoRegPublisher;
import app.redqueen.model.UserTeamo;
import app.redqueen.util.EmailAddressGen;
import integration.dto.reg.AgeSeek;
import integration.dto.reg.BirthDto;
import integration.dto.reg.RegTeamoUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class BotOrderService
{
    @Autowired
    TeamoAutoRegPublisher autoRegPublisher;

    public void orderBot(UserTeamo userTeamo)
    {
        userTeamo.setEmail(EmailAddressGen.mailRu());
        userTeamo.setPassword(EmailAddressGen.gmailCom());

        autoRegPublisher.sendRegUserTeamo(createRegUserDto(userTeamo));
    }

    private RegTeamoUserDto createRegUserDto(UserTeamo userTeamo)
    {
        LocalDateTime currDate = LocalDateTime.now();
        Random rand = new Random();

        int dayNumber = rand.nextInt(1, 28);
        int monthNumber = rand.nextInt(1, 11);

        return RegTeamoUserDto.builder()
                .name(userTeamo.getName())
                .birth(new BirthDto(
                        dayNumber,
                        monthNumber,
                        currDate.getYear() - userTeamo.getAge()
                ))
                .gender(userTeamo.getGender())
                .email(userTeamo.getEmail())
                .password(userTeamo.getPassword())
                .resPlaceIndex(getResPlaceIndexByName("City"))
                .ageSeek(new AgeSeek(
                        18, 40
                ))
                .education("1")
                .activity("6")
                .income("1")
                .maritalStatus("1")
                .children("1")
                .religion("8")
                .height("178")
                .smoking("1")
                .alcohol("1")
                .missNextOptStep("0")
                .build();
    }

    private int getResPlaceIndexByName(String cityName)
    {
        return 2085;
    }
}
