package model;

import app.redqueen.model.UserTeamo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTeamoModuleTest
{
    @Test
    void testBuilder_fieldCreateSourceNotNull()
    {
        //given
        UserTeamo userTeamo = UserTeamo.builder().build();
        //then
        //expected
        assertNotNull(userTeamo.getCreateSource());
    }

    @Test
    void testNoArgsConstructor_fieldCreateSourceNotNull()
    {
        //given
        UserTeamo userTeamo = new UserTeamo();
        //then
        //expected
        assertNotNull(userTeamo.getCreateSource());
    }
}
