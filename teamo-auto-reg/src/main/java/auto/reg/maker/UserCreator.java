package auto.reg.maker;

import auto.reg.integration.mq.reqQueen.RedQueenPublisher;
import auto.reg.integration.rest.teamo.TeamoClient;
import auto.reg.mapper.NextStepMapper;
import com.fasterxml.jackson.databind.JsonNode;
import integration.dto.UserTeamoDto;
import integration.dto.reg.RegTeamoUserDto;
import integration.dto.reg.RegTeamoUserImgDto;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class UserCreator
{
    @Autowired
    TeamoClient teamoClient;

    @Autowired
    RegPasser regPasser;

    @Autowired
    UserDtoReceiver userDtoReceiver;

    @Autowired
    ImageUploader imageUploader;

    @Autowired
    HumanImitator humanImitator;

    @Autowired
    RedQueenPublisher redQueenPublisher;

    public synchronized UserTeamoDto createUser(RegTeamoUserImgDto regTeamoUserDto)
    {
        try
        {
            regPasser.bypass(regTeamoUserDto.getUserDto());
        }
        catch (Exception ex)
        {
            log.warn(ex.getMessage());
            createUser(regTeamoUserDto);
        }

        Cookie teamoCookie = new Cookie("teamo",regPasser.getTeamoCookie().getValue());

        UserTeamoDto userTeamoDto = userDtoReceiver.receive(teamoCookie);
        userTeamoDto.setEmail(regTeamoUserDto.getUserDto().getEmail());
        userTeamoDto.setPassword(regTeamoUserDto.getUserDto().getPassword());

        teamoClient.setToken(userTeamoDto.getToken());

        String nextStepMsg = getNextStep();
        if (nextStepMsg.equals("faces"))
        {
            throw new RuntimeException("User already on 'faces' step");
        }
        log.info("Next step {} before edit user info", nextStepMsg);
        editMainInfo(regTeamoUserDto.getUserDto());

        log.info("Next step {} before bypass psy test", getNextStep());
        passPsyTest();

        log.info("Next step {} before upload avatar image", getNextStep());
        imageUploader.uploadAvaImg(teamoCookie, regTeamoUserDto.getImageDto());

        log.info("Next step {} before skip confirmation", getNextStep());
        skipConfirmation();

        log.info("Next step {} before human imitation" , getNextStep());
        humanImitator.userDoSomeStuff(teamoCookie);

        log.info("Success reg {} {}", userTeamoDto.getUserId() , userTeamoDto.getToken());
        redQueenPublisher.sendUserTeamo(userTeamoDto);
        return userTeamoDto;
    }

    private String getNextStep()
    {
        try
        {
            syncRandWait(15);
            return NextStepMapper.result(teamoClient.getNextStep());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("Getting next step error");
    }

    private JsonNode editMainInfo(RegTeamoUserDto regTeamoUserDto)
    {
        try
        {
            return teamoClient.editMainInfo(regTeamoUserDto);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("Edit main info error");
    }

    private JsonNode passPsyTest()
    {
        try
        {
            return teamoClient.cancelPsychoTesting();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("passPsyTest error");
    }

    private JsonNode skipConfirmation()
    {
        try

        {
            return teamoClient.skipConfirmation();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("skipConfirmation error");
    }

    private void syncRandWait(int waitSec)
    {
        Random random = new Random();
        waitSec = waitSec == 0 ? random.nextInt(50, 120) : waitSec;
        try {

            synchronized (Thread.currentThread())
            {
                Thread.currentThread().wait(1000L * waitSec);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
