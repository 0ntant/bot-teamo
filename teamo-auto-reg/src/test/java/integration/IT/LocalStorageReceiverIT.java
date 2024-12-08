package integration.IT;

import auto.reg.TeamoAutoReg;
import auto.reg.maker.LocalStorageReceiver;
import integration.dto.UserTeamoDto;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = TeamoAutoReg.class)
public class LocalStorageReceiverIT
{
    @Autowired
    LocalStorageReceiver localStorageReceiver;

    @Test
    void getKeyValueResult_success()
    {
        //given
        Cookie teamoCookie = new Cookie(
                "teamo",
                "bbb5f68935066023a4bcdcc8af9e711e:1de5cc177ebefce3c650ad23128b56ef89f7d44b"
        );
        Cookie rememberCookie = new Cookie(
                "teamo_remember_v2",
                "a9bbc6b645b816fb22958782"
        );

        //then
        UserTeamoDto userTokenIdData = localStorageReceiver.receive(List.of(teamoCookie, rememberCookie));

        //expected
        System.out.println(userTokenIdData.getToken());
        System.out.println(userTokenIdData.getUserId());
    }
}
