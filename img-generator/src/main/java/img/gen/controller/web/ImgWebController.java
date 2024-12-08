package img.gen.controller.web;

import img.gen.service.ImgService;
import img.gen.service.PhotoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("web/img")
public class ImgWebController
{
    @Autowired
    ImgService imgService;

    @Autowired
    PhotoQueryService photoQueryServ;

    @GetMapping
    public String getIndexValidator(Model model)
    {
        imgService.createPhotoFromRandChannel();
        if (imgService.getImgCurCount() == 0)
        {
            return "404";
        }

        String femaleRequests = String.join("\n",photoQueryServ.getFemaleRequests());
        String maleRequests = String.join("\n",photoQueryServ.getMaleRequests());

        model.addAttribute("maleRequests",maleRequests);
        model.addAttribute("femaleRequests",femaleRequests);

        return "img/index";
    }
}
