package app.service.controller.rest;

import app.service.dto.output.ImgDto;
import app.service.model.ImageAvatar;
import app.service.service.ImageAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "image-avatar")
public class ImgAvatarController
{
    @Autowired
    ImageAvatarService imgAvatarServ;

    @GetMapping("get/info-imgData-struct/by-gender")
    public ImgDto getInfoStrutByGender(@RequestParam("gender") String gender)
    {
        ImageAvatar imageAvatar = imgAvatarServ.getRandImgAvatarByGender(validateGender(gender));
        return ImgDto.imageAvatarToImgDto(imageAvatar);
    }

    @GetMapping("get/image/by-gender")
    public ResponseEntity<byte[]> getImageByGender(@RequestParam("gender") String gender) throws Exception
    {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imgAvatarServ.getRandImgBytesByGender(validateGender(gender)));
    }

    @GetMapping("get/image-bytes/by-gender")
    public byte[] getImageBytesByGender(@RequestParam("gender") String gender)
    {
        return imgAvatarServ.getRandImgBytesByGender(validateGender(gender));
    }

    @GetMapping("get/count/by-gender")
    public int getCountByGender(@RequestParam("gender") String gender)
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
