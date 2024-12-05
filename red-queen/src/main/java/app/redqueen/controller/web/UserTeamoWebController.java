package app.redqueen.controller.web;

import app.redqueen.dto.output.UserTeamoBlockDto;
import app.redqueen.dto.output.UserTeamoDto;
import app.redqueen.dto.output.UserTeamoFullInfo.UserTeamoFullInfoDto;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.UserTeamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("web/user-teamo")
public class UserTeamoWebController
{
    @Autowired
    private UserTeamoService userTeamoService;

    @GetMapping(path = "get/upload-bot-form")
    public String getCreateBotForm()
    {
        return "user-teamo/createBotForm";
    }

    @GetMapping(path = "get/send-message-form")
    public String sendMessageForm()
    {
        return "user-teamo/sendMessageForm";
    }

    @GetMapping(path = "v2/get/send-message-form")
    public String sendMessageFormV2(Model model)
    {
        model.addAttribute("usersWithToken",
                userTeamoService.findUsersWithTokenAndNotBlocking());
        return "v2/user-teamo/sendMessageForm";
    }

    @GetMapping
    public String getIndex()
    {
        return "user-teamo/index";
    }

    @GetMapping("get/list")
    public String getUsersList(
            Model model,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
    )
    {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (sortDir.equalsIgnoreCase("asc"))
        {
            sortDirection = Sort.Direction.ASC;
        }
        List<UserTeamoDto> userTeamoDtoList =  userTeamoService
                .findAllPages(
                        PageRequest.of(
                                offset,
                                limit,
                                Sort.by(
                                    sortDirection,
                                    sortBy
                                )
                        )
                )
                .get()
                .map(UserTeamoDto::mapToTeamoUserDto)
                .toList();

        model.addAttribute("usersTeamo", userTeamoDtoList);
        return "user-teamo/listAll";
    }

    @GetMapping("get/list-all")
    public String getUsersList(Model model)
    {
        List<UserTeamoDto> userTeamoDtoList =  userTeamoService
                .getAll()
                .stream()
                .map(UserTeamoDto::mapToTeamoUserDto)
                .toList();
        model.addAttribute("usersTeamo", userTeamoDtoList);
        return "user-teamo/listAll";
    }

    @GetMapping("get/info/{id}")
    public String getCount(Model model, @PathVariable("id") long id)
    {
        UserTeamo userTeamo = userTeamoService.findById(id);

        model.addAttribute(
                "userTeamo",
                UserTeamoFullInfoDto.mapToUserTeamoFullInfoDto(userTeamo)
        );
        return "user-teamo/info";
    }

    @GetMapping("get/users-with-token-not-blocked-list")
    public String getUsersWithToken(Model model)
    {
        List<UserTeamoDto> userTeamoDtoList = userTeamoService
                .findUsersWithTokenAndNotBlocking()
                .stream()
                .map(UserTeamoDto::mapToTeamoUserDto)
                .toList();
        model.addAttribute("usersTeamo", userTeamoDtoList);
        return "user-teamo/listAll";
    }

    @GetMapping("get/blacklist-all")
    public String getBlacklist(Model model)
    {
        List<UserTeamoBlockDto> users = new ArrayList<>();
        List<UserTeamo> blacklistOwners = userTeamoService.findBlackListOwners();
        for(UserTeamo blacklistOwner : blacklistOwners)
        {
            users.addAll(blacklistOwner.getBlackListsOwners()
                    .stream()
                    .map(userTeamo -> new UserTeamoBlockDto(blacklistOwner.getId(),userTeamo.getId()))
                    .toList());
        }
        model.addAttribute("users", users);
        return "user-teamo/blacklist";
    }

    @GetMapping("get/order-form")
    public String getOrderForm()
    {
        return "user-teamo/createOrderForm";
    }
}
