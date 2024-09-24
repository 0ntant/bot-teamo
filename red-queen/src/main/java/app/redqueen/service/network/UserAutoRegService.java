package app.redqueen.service.network;

import app.redqueen.integration.teamoAutoReg.UserAutoRegClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAutoRegService
{
    @Autowired
    UserAutoRegClient autoRegClient;

    public int getUserQueSize()
    {
        return autoRegClient.getUserQueSize();
    }

}
