package auto.reg.controller.rest;

import auto.reg.service.UsersOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users-order")
public class UsersOrderController
{
    @Autowired
    UsersOrderService usersOrderService;

    @GetMapping("get/user-queue-size")
    public int getUserQueSize()
    {
        return usersOrderService.getUserQueSize();
    }
}
