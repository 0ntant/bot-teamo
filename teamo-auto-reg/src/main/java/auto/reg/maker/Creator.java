package auto.reg.maker;

import auto.reg.integration.rest.contentStorageService.ImgAvaClient;
import auto.reg.integration.rest.teamo.TeamoClient;
import auto.reg.mapper.NextStepMapper;
import auto.reg.util.TempPhotoFileUtil;
import com.fasterxml.jackson.databind.JsonNode;
import integration.dto.UserTeamoDto;
import integration.dto.reg.RegTeamoUserDto;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;

import java.util.Random;

@Deprecated
@Slf4j
public class Creator
{
    //steps

    //questionnaire
    //psycho_testing
    //upload_photo_for_registration
    //profile_confirmation_by_phone
    //faces

    TeamoClient teamoClient;
    RegPasser regPasser;
    UserDtoReceiver userDtoReceiver;
    ImageUploader imageUploader;
    HumanImitator humanImitator;
    ImgAvaClient imgAvaClient;

    public Creator()
    {
        teamoClient = new TeamoClient();
        regPasser = new RegPasser();

        userDtoReceiver = new UserDtoReceiver();
        imageUploader = new ImageUploader();
        humanImitator = new HumanImitator();
        imgAvaClient = new ImgAvaClient();
    }

    public void createUser(RegTeamoUserDto regTeamoUserDto)
    {
        Cookie teamoCookie = null;
        try
        {
            regPasser.bypass(regTeamoUserDto);
        }
        catch (Exception ex)
        {
            log.warn(ex.getMessage());
            createUser(regTeamoUserDto);
        }

        String imgName = "";
        if(imgAvaClient.getCountByGender(regTeamoUserDto.getGender()) < 1)
        {
            syncRandWait(0);
            log.warn("No images gender={}, Content storage ", regTeamoUserDto.getGender());
            createUser(regTeamoUserDto);
        }
        else
        {
            imgName = TempPhotoFileUtil.saveFromImgAvaDto(imgAvaClient.getImgAvaDtoByGender(
                    regTeamoUserDto.getGender()
            ));
        }

        teamoCookie = new Cookie("teamo",regPasser.getTeamoCookie().getValue());

        log.info("In creator cookie value {}", teamoCookie.getValue());
        UserTeamoDto userTeamoDto = userDtoReceiver.receive(teamoCookie);

        teamoClient.setToken(userTeamoDto.getToken());

        log.info("Next step {} before edit user info", getNextStep());
        editMainInfo(regTeamoUserDto);

        log.info("Next step {} before bypass psy test", getNextStep());
        passPsyTest();

        log.info("Next step {} before upload avatar image", getNextStep());
        imageUploader.uploadAvaImg(
                teamoCookie,
                TempPhotoFileUtil.getFilePathByName(imgName));

        log.info("Next step {} before skip confirmation", getNextStep());
        skipConfirmation();
        TempPhotoFileUtil.removeByName(imgName);

        log.info("Next step {} before human imitation" , getNextStep());
        humanImitator.userDoSomeStuff(teamoCookie);

        log.info("Success reg {} {}", userTeamoDto.getUserId() , userTeamoDto.getToken());
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
