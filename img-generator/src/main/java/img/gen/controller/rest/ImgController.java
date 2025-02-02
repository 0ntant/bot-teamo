package img.gen.controller.rest;

import img.gen.dto.out.ImgDto;
import img.gen.service.ImgServiceImpl;
import img.gen.util.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(path = "img")
public class ImgController
{
    @Autowired
    ImgServiceImpl imgServiceImpl;

    @GetMapping("get/rand")
    public ImgDto getRandImg() {
        String imgPath = imgServiceImpl.getRandImgPath();

        return new ImgDto(
                FileUtil.getName(imgPath),
                FileUtil.getFileBytes(imgPath)
        );
    }

    @GetMapping("get/accept")
    public String accept(@RequestParam("gender") String gender,
                         @RequestParam("imgName") String imgName)
    {
        gender = gender.toLowerCase();
        if(!gender.equals("male") && !gender.equals("female"))
        {
            return "gender error";
        }

        if(!imgServiceImpl.imgExists(imgName))
        {
            return "img not found";
        }
        imgServiceImpl.createPhotoFromRandChannel();
        return imgServiceImpl.saveToCSS(gender, imgName);
    }

    @GetMapping("get/all-img-names")
    public List<String> getAllImgNames()
    {
        return imgServiceImpl.getAllImgNames();
    }

    @GetMapping("get/rand-img-name")
    public String getRandImgName()
    {
        return FileUtil.getName(imgServiceImpl.getRandImgPath());
    }

    @GetMapping("get/by-name/{name}")
    public ResponseEntity<Resource> getImageByName(@PathVariable("name") String name) throws Exception
    {
        File imgPath = imgServiceImpl.getByName(name).get();
        Path path = Paths.get(imgPath.getAbsolutePath());
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("get/randImg")
    public ResponseEntity<Resource> getImage() throws Exception
    {
        String imgPath = imgServiceImpl.getRandImgPath();
        Path path = Paths.get(imgPath);
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @DeleteMapping("delete/{imgName}")
    public String deleteByName(@PathVariable(name = "imgName") String imgName)
    {
        imgServiceImpl.createPhotoFromRandChannel();
        return imgServiceImpl.regToCSS(imgName);
    }
}
