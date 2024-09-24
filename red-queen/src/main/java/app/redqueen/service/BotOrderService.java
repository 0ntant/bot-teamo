package app.redqueen.service;

import app.redqueen.integration.teamoAutoReg.TeamoAutoRegPublisher;
import app.redqueen.model.ResidencePlace;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.ResidencePlaceService;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.ContentStorageService;
import app.redqueen.service.network.LocationService;
import app.redqueen.service.network.UserAutoRegService;
import app.redqueen.util.EmailAddressGen;
import app.redqueen.util.HumanNameGen;
import integration.dto.reg.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Queue;
import java.util.Random;

@Service
public class BotOrderService
{
    private static final Logger log = LoggerFactory.getLogger(BotOrderService.class);
    @Autowired
    private TeamoAutoRegPublisher autoRegPublisher;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserTeamoService userTeamoServ;

    private int usersLimit = 1;

    private Random random = new Random();

    @Autowired
    private ResidencePlaceService resPlaceServ;

    @Autowired
    private UserAutoRegService userAutoRegService;

    @Autowired
    private ContentStorageService contentStorageService;

    public void orderUser()
    {
        //int currUserAutoRegQueValue =  userAutoRegService.getUserQueSize();
        if (isQueueFull())
        {
            log.warn("Teamo-auto-reg service queue full ");
            return;
        }

        List<ResidencePlace> residencePlacesToOrderUsers = resPlaceServ.findActive();

        residencePlacesToOrderUsers = residencePlacesToOrderUsers
                .stream()
                .filter(residencePlace ->
                        isUsersInResPlace(residencePlace)
                                && isTimeResPlaceRangeValid(residencePlace)
                )
                .toList();

        if(residencePlacesToOrderUsers.isEmpty())
        {
            log.warn("No cities with valid time to order users");
            return;
        }

        String city = residencePlacesToOrderUsers.get(random.nextInt(
                0,
                residencePlacesToOrderUsers.size())
        ).getTitle();

        List<UserTeamo> usersInCity =  userTeamoServ.findBotsByCity(city);

        String genderUsersToOrder ;

        if (usersInCity.isEmpty())
        {
            genderUsersToOrder = "male";
        }
        else
        {
            genderUsersToOrder = "female";
        }

        if (contentStorageService.getCountByGender(genderUsersToOrder) <= 0)
        {
            log.warn("In content-storage-service not enough images for gender={}",
                    genderUsersToOrder
            );
            return;
        }

        if (genderUsersToOrder.equals("male"))
        {
            orderMaleInCity(city);
        }
        else
        {
            orderFemaleInCity(city);
        }
    }

    public boolean isQueueFull()
    {
        int currUserAutoRegQueValue =  userAutoRegService.getUserQueSize();
        return (currUserAutoRegQueValue >= 2);
    }

    public boolean isUsersInResPlace(ResidencePlace resPlaceToCheck)
    {
        return userTeamoServ.findBotsByCity(resPlaceToCheck.getTitle()).size() < usersLimit * 2;
    }

    public boolean isTimeResPlaceRangeValid(ResidencePlace resPlaceToCheck)
    {
        ZoneId resPlaceTimeZone = ZoneId.of(resPlaceToCheck.getTimezone());
        ZonedDateTime resPlaceTime = ZonedDateTime.now().withZoneSameInstant(resPlaceTimeZone);

        LocalDate currDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth()
        );
        ZonedDateTime minTime = ZonedDateTime.of( currDate, LocalTime.of(8,  0), resPlaceTimeZone );
        ZonedDateTime maxTime = ZonedDateTime.of( currDate, LocalTime.of(23,  0), resPlaceTimeZone );

        return  resPlaceTime.isAfter(minTime) && resPlaceTime.isBefore(maxTime);
    }

    private void orderFemaleInCity(String city)
    {
        UserTeamo userToOrder = UserTeamo.builder()
                .gender("female")
                .name(HumanNameGen.getNameByGender("female"))
                .height(random.nextInt(167, 178))
                .age(random.nextInt(23, 27))
                .city(city)
                .build();

        orderBot(userToOrder);
    }

    private void orderMaleInCity(String city)
    {
        UserTeamo userToOrder = UserTeamo.builder()
                .gender("male")
                .name(HumanNameGen.getNameByGender("male"))
                .height(random.nextInt(179, 186))
                .age(random.nextInt(24, 29))
                .city(city)
                .build();
        orderBot(userToOrder);
    }

    public void orderBot(UserTeamo userTeamo)
    {
        orderUser(
                createRegUserImgDto(
                        createRegUserDto(userTeamo)
                )
        );
    }

    public void orderUser(RegTeamoUserImgDto userToReg)
    {
        autoRegPublisher.sendRegUserTeamo(userToReg);
    }

    private RegTeamoUserImgDto createRegUserImgDto(RegTeamoUserDto regTeamoUserDto)
    {
        ImageAvaDto imageAvaDto = contentStorageService.getImgDtoByGender(regTeamoUserDto.getGender());

        String gender = regTeamoUserDto.getGender().equals("male") ? "1" : "2";
        regTeamoUserDto.setGender(gender);
        return new RegTeamoUserImgDto(
                regTeamoUserDto,
                imageAvaDto,
                "teamo-auto-reg service"
        );
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
                .email(EmailAddressGen.mailRu())
                .password(EmailAddressGen.gmailCom())
                .resPlaceIndex(getResPlaceIndexByName(userTeamo.getCity()))
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
    }

    public int getResPlaceIndexByName(String cityName)
    {
        int cityIndex = 2085;
        List<Integer> cityIndexes= locationService.getLocationIndexes(cityName);

        if (!cityIndexes.isEmpty())
        {
            cityIndex = cityIndexes.get(0);
        }
        return cityIndex;
    }
}
