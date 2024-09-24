package img.gen.controller.rest;

import img.gen.dto.input.ImgDataDto;
import img.gen.service.ImgProcessingService;
import img.gen.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="img-processing")
public class ImgProcessingController
{
    @Autowired
    ImgProcessingService imgProcessServ;

    @PostMapping("edit/flip/horizontal")
    public byte[] horizontalFlip(@RequestBody byte[] imgData)
    {
        return imgProcessServ.horizontalFlip(imgData);
    }

    @PostMapping("edit/{size}/cut/upper")
    public byte[] cutUpper(
            @RequestBody byte[] imgData,
            @PathVariable(name = "size") int size)
    {

        return imgProcessServ.cutUpper(
                imgData,
                size
        );
    }

    @PostMapping("edit/{size}/cut/bottom")
    public byte[] cutBottom(
            @RequestBody byte[] imgData,
            @PathVariable(name = "size") int size)
    {

        return imgProcessServ.cutBottom(
                imgData,
                size
        );
    }

    @PostMapping("edit/{size}/cut/right")
    public byte[] cutRight(
            @RequestBody byte[] imgData,
            @PathVariable(name = "size") int size)
    {
        return imgProcessServ.cutRight(
                imgData,
                size
        );
    }

    @PostMapping("edit/{size}/cut/left")
    public byte[] cutLeft(
            @RequestBody byte[] imgData,
            @PathVariable(name = "size") int size)
    {
        return imgProcessServ.cutLeft(
                imgData,
                size
        );
    }

    @PostMapping("edit/flip/horizontal/base-64")
    public String horizontalFlipBase64(@RequestBody ImgDataDto imgDataDto)
    {
        byte[] response = imgProcessServ.horizontalFlip(
                Base64Util.decode(
                        imgDataDto.base64().getBytes()
                )
        );

        return new String(Base64Util.encode(response));
    }

    @PostMapping("edit/{size}/cut/upper/base-64")
    public String cutUpperBase64(
            @RequestBody ImgDataDto imgDataDto,
            @PathVariable(name = "size") int size)
    {
        byte[] imgData = Base64Util.decode(
                imgDataDto.base64().getBytes()
        );
        byte[] response = imgProcessServ.cutUpper(
            imgData,
            size
        );

        return new String(response);
    }

    @PostMapping("edit/{size}/cut/bottom/base-64")
    public String cutBottomBase64(
            @RequestBody ImgDataDto imgDataDto,
            @PathVariable(name = "size") int size)
    {
        byte[] imgData = Base64Util.decode(
                imgDataDto.base64().getBytes()
        );
        byte[] response = imgProcessServ.cutBottom(
                imgData,
                size
        );

        return new String(response);
    }

    @PostMapping("edit/{size}/cut/right/base-64")
    public String cutRightBase64(
            @RequestBody ImgDataDto imgDataDto,
            @PathVariable(name = "size") int size)
    {
        byte[] imgData = Base64Util.decode(
                imgDataDto.base64().getBytes()
        );
        byte[] response = imgProcessServ.cutRight(
                imgData,
                size
        );

        return new String(response);
    }

    @PostMapping("edit/{size}/cut/left/base-64")
    public String cutLeftBase64(
            @RequestBody ImgDataDto imgDataDto,
            @PathVariable(name = "size") int size)
    {
        byte[] imgData = Base64Util.decode(
                imgDataDto.base64().getBytes()
        );
        byte[] response = imgProcessServ.cutLeft(
                imgData,
                size
        );

        return new String(response);
    }
}
