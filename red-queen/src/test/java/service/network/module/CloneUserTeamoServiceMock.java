package service.network.module;


import app.redqueen.integration.GeneralClient;
import app.redqueen.model.Photo;
import app.redqueen.model.ResidencePlace;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.BotOrderService;
import app.redqueen.service.CloneUserTeamoService;
import app.redqueen.service.database.ResidencePlaceService;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.ContentStorageService;
import app.redqueen.service.network.ImgGeneratorService;
import integration.dto.reg.RegTeamoUserImgDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CloneUserTeamoServiceMock
{
    @Mock
    BotOrderService botOrderService;

    @Mock
    UserTeamoService userTeamoService;

    @Mock
    ContentStorageService contentStorageService;

    @Mock
    ImgGeneratorService imgGeneratorService;

    @Mock
    GeneralClient generalClient;

    @Mock
    ResidencePlaceService residencePlaceService;

    @Spy
    @InjectMocks
    CloneUserTeamoService cloneUserTeamoServ;

    @Test
    void cloneUser_successCallBotOrderServiceOrderUse()
    {
        //given
        List<ResidencePlace> residencePlaces = List.of(
                new ResidencePlace(1L, "City1", "timezone1", true),
                new ResidencePlace(2L, "City2", "timezone2", true),
                new ResidencePlace(3L, "City1", "timezone3", true)
        );
        Photo userTeamoToClonePhoto = new Photo();
        userTeamoToClonePhoto.setTeamoUrl("url");

        UserTeamo userTeamoToClone = UserTeamo.builder()
                .id(12345111L)
                .name("Name")
                .name("City1")
                .gender("male")
                .photos(List.of(userTeamoToClonePhoto))
                .age(24)
                .build();

        doReturn(false).when(botOrderService).isQueueFull();
        doReturn(2011).when(botOrderService).getResPlaceIndexByName(anyString());
        doReturn(residencePlaces).when(residencePlaceService).findActive();
        doReturn(testedImgData()).when(generalClient).getFileData(anyString());
        doReturn(testedImgData()).when(imgGeneratorService).horizontalFlip(testedImgData());
        doReturn(false).when(contentStorageService).isObjectReg(testedImgData());

        //then
        cloneUserTeamoServ.orderCloneUser(userTeamoToClone);

        //expected
        verify(contentStorageService, times(1)).regObject(any());
        verify(residencePlaceService, times(1)).findActive();
        verify(botOrderService, times(1)).orderUser(any());
    }

    byte[] testedImgData()
    {
        try
        {
            return Files.readAllBytes(Path.of("src/test/resources/images/img3.png"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
