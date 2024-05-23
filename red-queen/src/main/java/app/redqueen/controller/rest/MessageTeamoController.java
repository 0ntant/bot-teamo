package app.redqueen.controller.rest;

import app.redqueen.dto.output.MessageDto;
import app.redqueen.dto.output.UserTeamoDto;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.MessageTeamoService;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.MessageNetServiceFactory;
import app.redqueen.service.network.MessageTeamoNetworkService;
import app.redqueen.service.network.ResultOrError;
import jakarta.el.MethodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "teamo-message")
public class MessageTeamoController
{
    @Autowired
    private MessageTeamoService messageTeamoServ;

    @Autowired
    private UserTeamoService userTeamoServ;

    @Autowired
    private MessageNetServiceFactory messageNetServiceFactory;

    @GetMapping("get/chat/{senderId}/{receiverId}")
    public List<MessageDto> getChat(@PathVariable("senderId") long senderId,
                                    @PathVariable("receiverId") long receiverId
    )
    {
        UserTeamo sender = userTeamoServ.findById(senderId);
        UserTeamo receiver = userTeamoServ.findById(receiverId);
        MessageTeamoNetworkService msgService;
        List<MessageTeamo> chat;

        if (sender.getToken() != null)
        {
             msgService
                    = messageNetServiceFactory.createMessageNetService(sender);
        }
        else
        {
             msgService
                    = messageNetServiceFactory.createMessageNetService(receiver);
        }

        msgService.getShareableMessages(sender, receiver, 20, 0);
        chat = messageTeamoServ.findChat(sender, receiver);

        return chat.stream()
                .map(MessageDto::mapToMessageDto)
                .toList();
    }

    @GetMapping("get/filter")
    public  List<MessageDto> getFilteredMessages(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
    )
    {
        throw new UnsupportedOperationException("In progress");
    }

    @GetMapping("get/all")
    public List<MessageDto> getAllMessages()
    {
        return messageTeamoServ.getAll()
                .stream()
                .map(MessageDto::mapToMessageDto)
                .toList();
    }
}
