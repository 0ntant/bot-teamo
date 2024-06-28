package auto.reg.service;

import auto.reg.maker.UserCreator;
import integration.dto.reg.RegTeamoUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
@Slf4j
public class UsersToOrderQueueService
{
    @Autowired
    private UserCreator userCreator;

    private Queue<RegTeamoUserDto> usersToOrder = new LinkedList<>();

    public void addUserToQue(RegTeamoUserDto userToAdd)
    {
        log.info("Add user name={} to queue", userToAdd.getName());
        usersToOrder.add(userToAdd);
    }

    public int getUserQueSize()
    {
        return usersToOrder.size();
    }

    public void regUser()
    {
        userCreator.createUser(usersToOrder.poll());
    }
}
