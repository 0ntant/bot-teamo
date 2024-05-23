package app.redqueen.service.database;

import app.redqueen.model.*;

import app.redqueen.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceDataFacade
{
    @Autowired
    LikeRepository likeRep ;

    @Autowired
    DislikeRepository dislikeRep;

    @Autowired
    GeneralAttributeRepository generalAttributeRep;

    @Autowired
    LookingTypeRepository lookingTypeRep;

    @Autowired
    LifestyleRepository lifestyleRepository;

    @Autowired
    LifestyleTypeRepository lifestyleTypeRepository;


    @Autowired
    UserTeamoRepository userTeamoRepository;
    @Transactional
    public UserTeamo synchronizeDbSave(UserTeamo userTeamo)
    {
        List<Like> likeList = userTeamo.getLikeList();
        for (int i = 0; i < likeList.size(); i++) {
            Like like = likeList.get(i);
            Optional<Like> likeInDB =
                    likeRep.findByNameAndText(
                            like.getName(),
                            like.getText()
                    );
            if (likeInDB.isPresent()) {
                likeList.set(i, likeInDB.get());
            }
        }
        userTeamo.setLikeList(likeList);

        List<Dislike> dislikeList = userTeamo.getDislikeList();
        for (int i = 0; i < dislikeList.size(); i++) {
            Dislike dislike = dislikeList.get(i);
            Optional<Dislike> dislikeInDb =
                    dislikeRep.findByNameAndText(
                            dislike.getName(),
                            dislike.getText()
                    );
            if (dislikeInDb.isPresent()) {
                dislikeList.set(i, dislikeInDb.get());
            }
        }
        userTeamo.setDislikeList(dislikeList);

        List<GeneralAttribute> generalAttributeList = userTeamo.getGeneralAttributes();
        for (int i = 0; i < generalAttributeList.size(); i++)
        {
            GeneralAttribute generalAttribute = generalAttributeList.get(i);
            Optional<GeneralAttribute> generalAttributeInDb
                    = generalAttributeRep.findByValueTextAndName(
                    generalAttribute.getValueText(),
                    generalAttribute.getName()
            );
            if(generalAttributeInDb.isPresent())
            {
                generalAttributeList.set(i, generalAttributeInDb.get());
            }
        }
        userTeamo.setGeneralAttributes(generalAttributeList);

        List<UserLooking> lookingList = userTeamo.getUserLookings();
        List<UserLooking> lookingListNew = new ArrayList<>();
        for (UserLooking lookingUser : lookingList)
        {
            Optional<LookingType> lookingType
                    = lookingTypeRep.findByLabel(lookingUser.getLookingType().getLabel());

            lookingType.ifPresent(type -> lookingListNew.add(UserLooking
                    .builder()
                    .user(userTeamo)
                    .body(lookingUser.getBody())
                    .lookingType(type)
                    .build()));
        }
        userTeamo.setUserLookings(lookingListNew);

        List<Lifestyle> lifestyleList = userTeamo.getLifestyleList();
        List<Lifestyle> lifestyleListNew = new ArrayList<>();
        for (Lifestyle lifestyle : lifestyleList)
        {
            Optional<LifestyleType> lifestyleTypeOpt
                    = lifestyleTypeRepository
                    .findByLabel(lifestyle.getLifestyleType().getLabel());
            lifestyleTypeOpt.ifPresent(type-> lifestyleListNew.add(Lifestyle
                    .builder()
                    .user(userTeamo)
                    .body(lifestyle.getBody())
                    .lifestyleType(type)
                    .build()));

        }
        userTeamo.setLifestyleList(lifestyleListNew);

        userTeamo.setBlock(
                UserTeamoBlock.builder()
                        .isBlocking(false)
                        .user(userTeamo)
                        .blockDate(new Date())
                        .build()
        );

        userTeamo.setSysCreateDate(new Date());
        return userTeamo;
    }

    @Transactional
    public void addingBotToBlackList(UserTeamo userBot, UserTeamo blacklistOwner )
    {
        userBot = userTeamoRepository.findById(userBot.getId()).get();
        blacklistOwner = userTeamoRepository.findById(blacklistOwner.getId()).get();
        userBot.getBlackListsOwners().add(blacklistOwner);
        userTeamoRepository.save(userBot);
    }
}

