package app.service.controller.rest;

import app.service.service.ObjectSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "register")
public class RegisterController
{
    @Autowired
    ObjectSumService objectSumServ;

    @PostMapping("check/reg/status")
    public Boolean checkRegStatus(@RequestBody  byte[] imgData)
    {
        return objectSumServ.isObjectReg(imgData);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "reg/object")
    public String regObject(@RequestBody byte[] imgData)
    {
        objectSumServ.regObject(imgData);
        return "Success";
    }
}
