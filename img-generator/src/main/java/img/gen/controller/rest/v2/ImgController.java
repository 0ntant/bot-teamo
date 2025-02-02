package img.gen.controller.rest.v2;

import img.gen.dto.ImgDataDto;
import img.gen.service.ImgMemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("v2Controller")
@RequestMapping("v2/img")
public class ImgController
{
    @Autowired
    ImgMemService imgMemService;

    @GetMapping("get/rand")
    public ImgDataDto getRandImg()
    {
        return imgMemService.getRandImg();
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteImg(@RequestBody ImgDataDto img)
    {
        imgMemService.deleteImg(img);
        imgMemService.addRandImg();
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveImg(@RequestBody ImgDataDto img)
    {
        imgMemService.saveImg(img);
        imgMemService.addRandImg();
    }
}
