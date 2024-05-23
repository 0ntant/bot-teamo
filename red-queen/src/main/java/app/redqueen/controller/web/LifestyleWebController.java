package app.redqueen.controller.web;

import app.redqueen.dto.output.LifestyleDto;
import app.redqueen.model.LifestyleType;
import app.redqueen.service.database.LifestyleService;
import app.redqueen.service.database.LifestyleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(path = "lifestyle")
public class LifestyleWebController
{
    @Autowired
    LifestyleService lifestyleServ;

    @Autowired
    LifestyleTypeService lifestyleTypeServ;

    @GetMapping
    public String getIndex()
    {
        return "lifestyle/index";
    }

    @GetMapping("get/list-all")
    public String getListAll(Model model)
    {
        List<LifestyleDto> lifestyles =
                new ArrayList<>(lifestyleServ.getAll()
                        .stream()
                        .map(LifestyleDto::mapToLifestyleDTO)
                        .toList()
                );
        Collections.reverse(lifestyles);
        model.addAttribute("lifestyles", lifestyles);
        return "lifestyle/list";
    }

    @GetMapping("get/list/by-type-label/{label}")
    public String getListByTypeLabel(Model model,
                                     @PathVariable("label") String label)
    {
        LifestyleType lifestyleType = lifestyleTypeServ.findByLabel(label);
        List<LifestyleDto> lifestyles
                = new ArrayList<>(lifestyleType.getLifestyleList()
                .stream()
                .map(LifestyleDto::mapToLifestyleDTO)
                .toList()
        );
        Collections.reverse(lifestyles);
        model.addAttribute("lifestyles", lifestyles);
        return "lifestyle/list";
    }
}
