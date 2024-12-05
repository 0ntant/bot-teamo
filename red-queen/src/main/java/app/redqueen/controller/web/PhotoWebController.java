package app.redqueen.controller.web;

import app.redqueen.dto.output.PhotoDto;
import app.redqueen.service.database.UserTeamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("web/photo")
public class PhotoWebController
{
    @Autowired
    UserTeamoService userTeamoServ;

    @GetMapping("get/gallery")
    public String getGallery(
            Model model,
            @RequestParam(value = "gender", defaultValue = "female") String gender)
    {
        gender = gender.equals("male") ? "male" : "female";
        List<PhotoDto> photos = new ArrayList<>(userTeamoServ.findByGender(gender)
                .stream()
                .filter(userTeamo -> !userTeamo.getPhotos().isEmpty())
                .map(userTeamo -> PhotoDto.mapToPhotoDto(userTeamo.getPhotos().get(0)))
                .toList());
        Collections.reverse(photos);
        model.addAttribute("photos", photos);
        return "photo/gallery";
    }

    @GetMapping
    public String getIndex()
    {
        return "photo/index";
    }
}
