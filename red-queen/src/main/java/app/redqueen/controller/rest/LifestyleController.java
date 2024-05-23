package app.redqueen.controller.rest;


import app.redqueen.dto.output.LifestyleDto;
import app.redqueen.model.LifestyleType;
import app.redqueen.service.database.LifestyleService;
import app.redqueen.service.database.LifestyleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "lifestyle")
public class LifestyleController
{
    @Autowired
    LifestyleService lifestyleServ;

    @Autowired
    LifestyleTypeService lifestyleTypeServ;

    @GetMapping("get/all")
    public List<LifestyleDto> getAll()
    {
        return lifestyleServ.getAll()
                .stream()
                .map(LifestyleDto::mapToLifestyleDTO)
                .toList();
    }

    @Transactional
    @GetMapping("get/by-type-label/{label}")
    public List<LifestyleDto> getAll(@PathVariable("label") String label)
    {
        LifestyleType lifestyleType = lifestyleTypeServ.findByLabel(label);
        return lifestyleType.getLifestyleList()
                .stream()
                .map(LifestyleDto::mapToLifestyleDTO)
                .toList();
    }
}
