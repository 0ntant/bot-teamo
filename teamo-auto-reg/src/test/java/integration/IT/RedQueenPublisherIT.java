package integration.IT;

import auto.reg.Main;
import auto.reg.integration.mq.reqQueen.RedQueenPublisher;
import integration.dto.UserTeamoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Main.class)
public class RedQueenPublisherIT
{
    @Autowired
    RedQueenPublisher redQueenPublisher;

    @Test
    public void sendUser_successNoException()
    {
        //given
        UserTeamoDto userToSend = new UserTeamoDto(
                1L,
                "token",
                "email",
                "pass",
                "testCreateSource"
        );
        //then
        redQueenPublisher.sendUserTeamo(userToSend);
        //expected
    }
}
