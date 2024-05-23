package app.redqueen.controller.rest;

import app.redqueen.dto.input.BotDto;
import app.redqueen.dto.input.MessageDto;
import app.redqueen.dto.output.TeamoUserDto;
import app.redqueen.dto.output.UserTeamoDto;
import app.redqueen.dto.output.UserTeamoFullInfo.UserTeamoFullInfoDto;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.UserTeamoService;
import app.redqueen.service.network.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "user-teamo")
@Slf4j
public class UserTeamoController
{

   @Autowired
   private UserTeamoService userTeamoService;
   @Autowired
   private UserNetServiceFactory userNetServiceFactory;

   @Autowired
   private MessageNetServiceFactory messageNetServiceFactory;

   @PostMapping("create/bot")
   public String createBot(@RequestBody BotDto botDto)
   {
        //check user already exists
        boolean isUserExist = userTeamoService.isExistById(botDto.getId());
        if(isUserExist)
        {
            return "User already exist";
        }

        UserTeamo userTeamo = BotDto.BotDtoToUserTeamo(botDto);
        UserTeamoNetworkService userTeamoNetworkService
                = userNetServiceFactory.createUserNetDecorator(userTeamo);
        userTeamo.setSysCreateDate(new Date());

        ResultOrError<UserTeamo> resultOrError = userTeamoNetworkService.getSelfFullInfo();
        if (resultOrError.isErrorResponse())
        {
            return "Error during self info";
        }
        return "Success ";
   }

    @PostMapping("send-message")
    public app.redqueen.dto.output.MessageDto sendMessageByUser(@RequestBody MessageDto messageDto)
    {
        UserTeamo sender = userTeamoService.findById(messageDto.getUserWithTokenId());
        UserTeamo receiver = userTeamoService.findById(messageDto.getUserReceiverId());

        MessageTeamoNetworkService msgNetServ
                = messageNetServiceFactory.createMessageNetService(sender);

        log.info("send message from {} to {} msg {}",
                sender.getId(),
                receiver.getId(),
                messageDto.getMessage()
        );

        ResultOrError<MessageTeamo> resultOrError = msgNetServ.sendMessage(
                sender,
                receiver,
                messageDto.getMessage()
        );

        return  app.redqueen.dto.output.MessageDto.mapToMessageDto(resultOrError.getResult());
    }

    @GetMapping("get/users-with-token-not-blocked")
    public List<UserTeamoDto> getUsersWithToken()
    {
       return userTeamoService.findUserWithTokenAndNotBlocking()
               .stream()
               .map(UserTeamoDto::mapToTeamoUserDto)
               .toList();
    }

    @GetMapping("get/user-chat-partners/{userId}")
    public  List<TeamoUserDto> getUsersChatPartners(@PathVariable("userId") long userId)
    {
        UserTeamo userCheck = userTeamoService.findById(userId);

        return userTeamoService.findChatPartners(userCheck)
                .stream()
                .filter(userTeamo ->
                        userTeamo.getBlackList()
                                .stream()
                                .noneMatch(userInBlacklist -> Objects.equals(userInBlacklist.getId(), userCheck.getId())))
                .map(TeamoUserDto::mapToTeamoUserDto)
                .toList();
    }

    @GetMapping("get/full-info/{id}")
    public UserTeamoFullInfoDto getUserById(@PathVariable("id") long id)
    {
        return UserTeamoFullInfoDto.mapToUserTeamoFullInfoDto(userTeamoService.findById(id));
    }

    @GetMapping("get/count")
    public long getCount()
    {
        return userTeamoService.getCount();
    }
}
