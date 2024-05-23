package app.redqueen.controller.rest;

import app.redqueen.dto.output.LifestyleTypeDto;
import app.redqueen.service.database.LifestyleService;
import app.redqueen.service.database.LifestyleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "lifestyle-type")
public class LifestyleTypeController
{
    @Autowired
    LifestyleTypeService lifestyleTypeServ;

    @GetMapping("get/all")
    public List<LifestyleTypeDto> getAll()
    {
        return lifestyleTypeServ.getAll()
                .stream()
                .map(LifestyleTypeDto::mapToLifestyleTypeDto)
                .toList();
    }
}
