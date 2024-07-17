package app.redqueen.bot.auto;

import app.redqueen.model.BotPhrase;
import app.redqueen.model.BotPhraseType;
import app.redqueen.model.MessageTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.service.database.BotServiceFacade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@NoArgsConstructor
public class BotTeamoDBService
{
    @Getter
    @Setter
    UserTeamo userBot;

    @Getter
    @Setter
    private List<BotPhraseType> phraseTypeList;

    @Setter
    private BotServiceFacade botServiceFacade;

    public boolean isUserAddBotToBlackList(UserTeamo blackListOwner)
    {
        return botServiceFacade.isUserAddBotToBlackList(userBot, blackListOwner);
    }

    public  List<MessageTeamo> botsMessagesWithUser(UserTeamo checkedUser)
    {
       return botServiceFacade.botsMessagesWithUser(userBot, checkedUser);
    }

    public boolean dialogWithUserAlreadyExists(UserTeamo checkedUser)
    {
        return botServiceFacade.dialogWithUserAlreadyExists(phraseTypeList, userBot,checkedUser);
    }

    public boolean botHasLastMessage(UserTeamo partner)
    {
       return botServiceFacade.botHasLastMessage(userBot, partner);
    }

    public List<UserTeamo> getPartners()
    {
        return botServiceFacade.getPartners(userBot);
    }

    public List<BotPhrase> generateDialogForUser(UserTeamo userTeamo)
    {
        return botServiceFacade.generateDialogForUser(phraseTypeList, userBot, userTeamo);
    }

    public UserTeamo getUserById(long id )
    {
        return botServiceFacade.getUserById(id);
    }
}
