package app.service.controller.rest;

import app.service.dto.output.ImgDto;
import app.service.model.ImageAvatar;
import app.service.service.ImageAvatarService;
import app.service.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping(path = "image-avatar")
public class ImgAvatarController
{
    @Autowired
    ImageAvatarService imgAvatarServ;

    @GetMapping("get/info-base64-struct/by-gender")
    public ImgDto getInfoStrutByGender(@RequestParam("gender") String gender)
    {
        ImageAvatar imageAvatar = imgAvatarServ.getRandByGender(validateGender(gender));
        ImgDto imgDto =  ImgDto.imageAvatarToImgDto(imageAvatar);
        FileUtil.remove(imageAvatar.getPath());
        return imgDto;
    }

    @GetMapping("get/image/by-gender")
    public ResponseEntity<byte[]> getImageByGender(@RequestParam("gender") String gender) throws Exception
    {
        ImageAvatar imageAvatar = imgAvatarServ.getRandByGender(validateGender(gender));
        ResponseEntity<byte[]> response =   ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(FileUtil.getBytes(imageAvatar.getPath()));
        FileUtil.remove(imageAvatar.getPath());
        return response;
    }

    @GetMapping("get/image-bytes/by-gender")
    public byte[] getImageBytesByGender(@RequestParam("gender") String gender)
    {
        ImageAvatar imageAvatar = imgAvatarServ.getRandByGender(validateGender(gender));
        byte[] imgBytes =  FileUtil.getBytes(imageAvatar.getPath());
        FileUtil.remove(imageAvatar.getPath());
        return imgBytes;
    }

    @GetMapping("get/count/by-gender")
    public int getCountBytesByGender(@RequestParam("gender") String gender)
    {
        return imgAvatarServ.getCountByGender(validateGender(gender));
    }

    private String validateGender(String gender)
    {
        gender = gender.toLowerCase();
        gender = !Objects.equals(gender, "female") ? "male" : "female";
        return gender;
    }
}
