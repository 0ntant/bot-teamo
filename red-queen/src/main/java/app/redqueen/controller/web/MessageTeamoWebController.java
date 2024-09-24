package app.redqueen.controller.web;

import app.redqueen.dto.output.MessageDto;
import app.redqueen.dto.output.ChatDto;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.MessageTeamoService;
import app.redqueen.service.database.UserTeamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(path = "teamo-message")
public class MessageTeamoWebController
{
    @Autowired
    MessageTeamoService messageTeamoServ;

    @Autowired
    UserTeamoService userTeamoServ;

    @GetMapping
    public String getIndex()
    {
        return "messages/index";
    }

    @GetMapping("get/list-all")
    public String getListAll(Model model)
    {
        List<MessageDto> messageDtoList = new ArrayList<>(messageTeamoServ.getAll()
                .stream()
                .map(MessageDto::mapToMessageDto)
                .toList()
        );
        Collections.reverse(messageDtoList);
        model.addAttribute("messages", messageDtoList);
        return "messages/list";
    }

    @Transactional
    @GetMapping("get/chat")
    public String getChat(Model model,
                          @RequestParam(value = "senderId") long senderId,
                          @RequestParam(value = "receiverId") long receiverId
    )
    {
        UserTeamo sender = userTeamoServ.findById(senderId);
        UserTeamo receiver = userTeamoServ.findById(receiverId);
        List<MessageDto> messageDtoList = new ArrayList<>(messageTeamoServ
                .findChat(sender,receiver)
                .stream()
                .map(MessageDto::mapToMessageDto)
                .toList());
        Collections.reverse(messageDtoList);
        model.addAttribute("messages", messageDtoList);
        return "messages/list";
    }

    @Transactional
    @GetMapping("get/chats")
    public String getChat(Model model)
    {
        List<ChatDto> messageDtoList = new ArrayList<>();
        List<UserTeamo> usersWithTokens = userTeamoServ.findUsersWithToken();
        for(UserTeamo userWithTokens : usersWithTokens)
        {
            messageDtoList.addAll(userTeamoServ.findChatPartners(userWithTokens)
                    .stream()
                    .map(userTeamo -> ChatDto.mapTochatDto(
                            userTeamo
                                .getMessagesFromThisUser()
                                .get(0))
                    )
                    .toList());
        }
        model.addAttribute("chats", messageDtoList);
        return "messages/listChats";
    }
}
