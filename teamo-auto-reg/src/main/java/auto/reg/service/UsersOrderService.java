package auto.reg.service;

import auto.reg.maker.UserCreator;
import integration.dto.reg.RegTeamoUserImgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
@Slf4j
public class UsersOrderService
{
    @Autowired
    private UserCreator userCreator;

    private Queue<RegTeamoUserImgDto> usersImgToOrder = new LinkedList<>();

    public void addUserToQue(RegTeamoUserImgDto regTeamoUserImgDto)
    {
        log.info("Add user name={} to queue", regTeamoUserImgDto.getUserDto().getName());
        usersImgToOrder.add(regTeamoUserImgDto);
    }

    public int getUserQueSize()
    {
        return usersImgToOrder.size();
    }

    public void regUserFromPool()
    {
        if (getUserQueSize() > 0)
        {
            log.info("Order user from pool");
            regUser(usersImgToOrder.poll());
        }
    }

    public void regUser(RegTeamoUserImgDto userToReg)
    {
        userCreator.createUser(userToReg);
    }
}
