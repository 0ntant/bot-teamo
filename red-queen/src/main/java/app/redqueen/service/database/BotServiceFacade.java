package app.redqueen.service.database;

import app.redqueen.model.BotPhrase;
import app.redqueen.model.BotPhraseType;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BotServiceFacade
{
    @Autowired
    private DialogGenerator dialogGenerator;

    @Autowired
    private MessageTeamoService msgService;

    @Autowired
    UserTeamoService userTeamoService;

    @Transactional
    public boolean isUserAddBotToBlackList(UserTeamo userBot,UserTeamo blackListOwner)
    {
        boolean checkUserExist = userTeamoService.isExistById(blackListOwner.getId());
        if(!checkUserExist)
        {
            log.error("User id={} not found in DB", blackListOwner.getId());
            return true;
        }
        UserTeamo userBlackListOwner = userTeamoService.findById(blackListOwner.getId());

        boolean userBotInBlackList = false;
        for (UserTeamo userInBlackList : userBlackListOwner.getBlackList())
        {
            if (Objects.equals(userInBlackList.getId(), userBot.getId()))
            {
                userBotInBlackList = true;
                break;
            }
        }

        if (userBotInBlackList)
        {
            log.warn("User id={} added bot user id={} to blacklist",
                    blackListOwner.getId(),
                    userBot.getId());
        }
        return userBotInBlackList;
    }


    @Transactional
    public List<MessageTeamo> botsMessagesWithUser(UserTeamo userBot, UserTeamo checkedUser)
    {
        List<MessageTeamo> chat = msgService.findChat(userBot, checkedUser);
        return chat
                .stream()
                .filter(msg ->
                        Objects.equals(
                                msg.getUserSender().getId(),
                                userBot.getId()))
                .toList();
    }

    @Transactional
    public boolean dialogWithUserAlreadyExists(
            List<BotPhraseType> phraseTypeList,
            UserTeamo userBot,
            UserTeamo checkedUser)
    {
        List<MessageTeamo> messagesFromBotToUser =
                botsMessagesWithUser(userBot,checkedUser);
        if (messagesFromBotToUser.size() != phraseTypeList.size())
        {
            return false;
        }
        for (int i = 0; i < messagesFromBotToUser.size() ; i++)
        {
            if (!Objects.equals(messagesFromBotToUser.get(i).getBotPhraseType(), phraseTypeList.get(i)))
            {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public boolean botHasLastMessage(UserTeamo userBot, UserTeamo partner)
    {
        List<MessageTeamo> chat = msgService.findChat(userBot, partner);

        return !chat.isEmpty() &&
                Objects.equals(chat.get(chat.size() - 1).getUserSender().getId(), userBot.getId());

    }

    public List<UserTeamo> getPartners(UserTeamo userBot)
    {
        return userTeamoService.findChatPartners(userBot);
    }


    @Transactional
    public List<BotPhrase> generateDialogForUser(
            List<BotPhraseType> phraseTypeList,
            UserTeamo userBot,
            UserTeamo userDialogReceiver
    )
    {
        List<MessageTeamo> botSentPhrases = botsMessagesWithUser(userBot, userDialogReceiver);
        int phraseOffset = 0; // botsMessagesWithUser(userBot, userDialogReceiver).size();

        for (int i = 0; i<botSentPhrases.size() ; i++)
        {
            if (botSentPhrases.get(i).getBotPhraseType() != null &&
                Objects.equals(
                        botSentPhrases.get(i).getBotPhraseType().getId(),
                        phraseTypeList.get(i).getId())
            )
            {
                phraseOffset++;
            }
        }

        return dialogGenerator.genrateFromTypeList(
                phraseTypeList.subList(
                    phraseOffset,
                    phraseTypeList.size()
                ),
                userBot.getGender()
        );
    }

    public UserTeamo getUserById(long id)
    {
        return userTeamoService.findById(id);
    }
}
