package app.redqueen.service;

import app.redqueen.integration.GeneralClient;
import app.redqueen.model.ResidencePlace;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.ResidencePlaceService;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.ContentStorageService;
import app.redqueen.service.network.ImgGeneratorService;
import app.redqueen.util.Base64Util;
import app.redqueen.util.EmailAddressGen;
import integration.dto.reg.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@Slf4j
public class CloneUserTeamoService
{
    @Autowired
    BotOrderService botOrderService;

    @Autowired
    UserTeamoService userTeamoService;

    @Autowired
    ContentStorageService contentStorageService;

    @Autowired
    ImgGeneratorService imgGeneratorService;

    @Autowired
    GeneralClient generalClient;

    @Autowired
    ResidencePlaceService residencePlaceService;

    final String cloneTag = "clone";

    public List<UserTeamo> getAllClones()
    {
        return userTeamoService.findUsersWithToken()
                .stream()
                .filter(userTeamo -> userTeamo.getCreateSource().contains(cloneTag))
                .toList();
    }

    public void cloneRandomUser()
    {
        UserTeamo userToClone = userTeamoService.findUsersWithoutToken()
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("User without token not found"));
        orderCloneUser(userToClone);
    }

    public void orderCloneUser(UserTeamo userTeamoToClone)
    {
        if (userTeamoToClone.getCreateSource().contains(cloneTag))
        {
            log.error("Can't create clone from clone user ID={}",
                    userTeamoToClone.getId()
            );
            return;
        }
        if (isCloneExists(userTeamoToClone))
        {
            UserTeamo cloneUser = getClone(userTeamoToClone);
            log.warn("User id={} clone alreadyExists user ID={}",
                    userTeamoToClone.getId(),
                    cloneUser.getId()
            );
            return;
        }
        if (botOrderService.isQueueFull())
        {
            log.warn("Can't clone user ID={} queue is full",
                    userTeamoToClone.getId()
            );
            return;
        }
        byte[] userTeamoToCloneImgData = getImgData(usersFirstPhotoUrl(userTeamoToClone));
        userTeamoToCloneImgData = horizontalFlipImg(userTeamoToCloneImgData);
        if (isDataReg(userTeamoToCloneImgData))
        {
            log.warn("User to clone id={} img data already registered",
                    userTeamoToClone.getId()
            );
            return;
        }
        regImg(userTeamoToCloneImgData);

        botOrderService.orderUser(
                mapUserClone(
                        userTeamoToClone,
                        userTeamoToCloneImgData
                )
        );
    }

    private String usersFirstPhotoUrl(UserTeamo userTeamo)
    {
        return userTeamo.getPhotos()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User first photo url not found"))
                .getTeamoUrl();

    }

    public RegTeamoUserImgDto mapUserClone(UserTeamo userTeamo, byte[] imgData)
    {
        LocalDateTime currDate = LocalDateTime.now();
        RegTeamoUserImgDto userClone = new RegTeamoUserImgDto();
        Random rand = new Random();
        String gender = userTeamo.getGender().equals("male") ? "1" : "2" ;
        ResidencePlace anotherCity = findAnotherCity(userTeamo.getCity());
        int dayNumber = rand.nextInt(1, 28);
        int monthNumber = rand.nextInt(1, 11);

        RegTeamoUserDto userData = RegTeamoUserDto.builder()
                .name(userTeamo.getName())
                .birth(new BirthDto(
                        dayNumber,
                        monthNumber,
                        currDate.getYear() - userTeamo.getAge())
                )
                .gender(gender)
                .email(EmailAddressGen.mailRu())
                .password(EmailAddressGen.gmailCom())
                .resPlaceIndex(
                        botOrderService.getResPlaceIndexByName(
                                anotherCity.getTitle()
                        )
                )
                .ageSeek(new AgeSeek(
                        18, 40
                ))
                .education("1")
                .activity(String.valueOf(rand.nextInt(1, 10)))
                .income("1")
                .maritalStatus("1")
                .children("1")
                .religion("8")
                .height(String.valueOf(userTeamo.getHeight()))
                .smoking("1")
                .alcohol("1")
                .missNextOptStep("0")
                .build();

        ImageAvaDto userImg = new ImageAvaDto();
        userImg.setBase64(new String(Base64Util.encode(imgData)));
        userImg.setName(userImgNamePng(userTeamo));

        userClone.setUserDto(userData);
        userClone.setImageDto(userImg);
        userClone.setCreateSource(makeCreateSource(userTeamo.getId()));

        return userClone;
    }

    private ResidencePlace findAnotherCity(String cityName)
    {
        return residencePlaceService.findActive()
                .stream()
                .filter(residencePlace -> !residencePlace.getTitle().equals(cityName))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("ResidencePlace not found"));
    }

    private byte[] getImgData(String url)
    {
        return generalClient.getFileData(url);
    }

    private byte[] horizontalFlipImg(byte[] imgData)
    {
        return imgGeneratorService.horizontalFlip(imgData);
    }

    private boolean isDataReg(byte[] imgData)
    {
        return contentStorageService.isObjectReg(imgData);
    }

    private void regImg(byte[] imgData)
    {
        contentStorageService.regObject(imgData);
    }

    private UserTeamo getClone(UserTeamo userTeamo)
    {
        return userTeamoService.findByCreateSource(userTeamo.getCreateSource())
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User first photo url not found"));
    }

    private boolean isCloneExists(UserTeamo userTeamo)
    {
        String userCreateSource = makeCreateSource(userTeamo.getId());
        return !userTeamoService.findByCreateSource(userCreateSource).isEmpty();
    }

    private String makeCreateSource(long userId)
    {
        return String.format("%s%s",cloneTag,userId);
    }

    private String userImgNamePng(UserTeamo userTeamo)
    {
        return String.format("img%sRQ.png", userTeamo.getId());
    }
}
