package app.redqueen.controller.web;

import app.redqueen.dto.output.LifestyleTypeDto;
import app.redqueen.service.database.LifestyleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("web/lifestyle-type")
public class LifestyleTypeWebController
{
    @Autowired
    LifestyleTypeService lifestyleTypeServ;

    @GetMapping
    public String getIndex()
    {
        return "lifestyleType/index";
    }

    @GetMapping("get/list-all")
    public String getListAll(Model model)
    {
        List<LifestyleTypeDto> lifestyleTypeDtoList
                = lifestyleTypeServ.getAll()
                .stream()
                .map(LifestyleTypeDto::mapToLifestyleTypeDto)
                .toList();
        model.addAttribute("lifestyleTypes", lifestyleTypeDtoList);
        return "lifestyleType/listAll";
    }
}
