package img.gen.controller.web;

import img.gen.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "img")
public class ImgWebController
{

    @Autowired
    ImgService imgService;

    @GetMapping
    public String getIndexValidator(Model model)
    {
        if (imgService.getImgCurCount() == 0 )
        {
            return "404";
        }
        return "img/index";
    }
}
