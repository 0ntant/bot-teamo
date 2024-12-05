package img.gen.controller.rest;

import img.gen.dto.QueryDto;
import img.gen.service.PhotoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("photo-query")
public class PhotoQueryController
{
    @Autowired
    PhotoQueryService photoQueryServ;

    @GetMapping("get/all/{gender}")
    public List<QueryDto> getQueriesByGender(@PathVariable("gender") String gender)
    {
        gender = gender.equals("male") ? "male" : "female";
        if (gender.equals("male"))
        {
            return photoQueryServ.getMaleRequests()
                    .stream()
                    .map(QueryDto::new)
                    .toList();
        }

        return photoQueryServ.getFemaleRequests()
                .stream()
                .map(QueryDto::new)
                .toList();
    }

    @PostMapping("edit/{gender}")
    public List<QueryDto> editQueriesByGender(
            @PathVariable("gender") String gender,
            @RequestBody List<QueryDto> setQueries)
    {
        gender = gender.equals("male") ? "male" : "female";
        if (gender.equals("male"))
        {
            photoQueryServ.setMaleRequests(
                    setQueries
                            .stream()
                            .map(QueryDto::title)
                            .toList()
            );
            return photoQueryServ.getMaleRequests()
                    .stream()
                    .map(QueryDto::new)
                    .toList();
        }

        photoQueryServ.setFemaleRequests(
                setQueries
                        .stream()
                        .map(QueryDto::title)
                        .toList()
        );

        return photoQueryServ.getFemaleRequests()
                .stream()
                .map(QueryDto::new)
                .toList();
    }
}
