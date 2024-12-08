package app.redqueen.controller.web;

import app.redqueen.dto.output.PhotoDto;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.UserTeamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping("get/gallery/list")
    public String getGalleryList(
            Model model,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "8") Integer limit,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(value = "gender", defaultValue = "female") String gender)
    {
        gender = gender.equals("male") ? "male" : "female";
//        Page<UserTeamo> photos = new ArrayList<>(userTeamoServ.findByGender(gender)
//                .stream()
//                .filter(userTeamo -> !userTeamo.getPhotos().isEmpty())
//                .map(userTeamo -> PhotoDto.mapToPhotoDto(userTeamo.getPhotos().get(0)))

        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (sortDir.equalsIgnoreCase("asc"))
        {
            sortDirection = Sort.Direction.ASC;
        }

        Page<UserTeamo> photos = userTeamoServ
                .findByGender(
                        gender,
                        PageRequest.of(
                                offset,
                                limit,
                                Sort.by(
                                        sortDirection,
                                        sortBy
                                )
                        )
                );

        model.addAttribute("photos", photos
                .stream()
                .map(userTeamo -> PhotoDto.mapToPhotoDto(userTeamo.getPhotos().get(0)))
                .toList()
        );
        model.addAttribute("totalPages", photos.getTotalPages());
        model.addAttribute("currentPage", photos.getNumber());
        model.addAttribute("gender", gender);

        return "photo/galleryPage";
    }

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
