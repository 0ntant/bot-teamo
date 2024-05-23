package app.redqueen.mapper.user;

import app.redqueen.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserTeamoMapper implements JsonNodeToUserTemo
{
    private  JsonNodeToLikeList jsonNodeToLikeList;

    private JsonNodeToDislikeList jsonNodeToDislikeList;

    private JsonNodeToGeneralAttributeList jsonNodeToGeneralAttributeList;

    private JsonNodeToPhotoList jsonNodeToPhotoList;

    private JsonNodeToLifestyle jsonNodeToLifestyle;

    private JsonNodeToUserLooking jsonNodeToUserLooking;

    private final Logger logger = LoggerFactory.getLogger(UserTeamoMapper.class);

    public UserTeamoMapper()
    {
        this.jsonNodeToLikeList = new LikesTeamoMapper();
        this.jsonNodeToDislikeList = new DislikesTeamoMapper();
        this.jsonNodeToGeneralAttributeList = new GeneralAttributeMapper();
        this.jsonNodeToPhotoList = new PhotoTeamoMapper();
        this.jsonNodeToLifestyle = new LifestyleMapper();
        this.jsonNodeToUserLooking = new UserLookingMapper();
    }

    @Override
    public UserTeamo map(JsonNode jsonNode)
    {
        JsonNode profile = jsonNode.path("result").path("profile");
        UserTeamo userTeamo = new UserTeamo();

        long id = profile.path("id").asLong();

        String name = profile.path("name").asText();
        int age = profile.path("age").asInt();
        //2024-01-07 18:09:38
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate lastVisitLocalDate = LocalDate.parse(profile.path("last_visit").asText(), pattern);
        Date lastVisit = Date.from(
                lastVisitLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        String city = profile.path("city").asText();
        String zodiac = profile.path("zodiac").asText();
        int height = profile.path("height").asInt();
        String gender = profile.path("sex").asInt() == 1 ? "male" : "female";

        List<Like> likeList = jsonNodeToLikeList.map(jsonNode);
        List<Dislike> dislikeList = jsonNodeToDislikeList.map(jsonNode);
        List<GeneralAttribute> generalAttributeList = jsonNodeToGeneralAttributeList.map(jsonNode);
        List<Photo> photos = jsonNodeToPhotoList.map(jsonNode, userTeamo);
        List<Lifestyle> lifestyleList = jsonNodeToLifestyle.map(jsonNode, userTeamo);
        List<UserLooking> userLookingList = jsonNodeToUserLooking.map(jsonNode, userTeamo);

        userTeamo.setId(id);
        userTeamo.setName(name);
        userTeamo.setAge(age);
        userTeamo.setLastVisit(lastVisit);
        userTeamo.setSysCreateDate(new Date());
        userTeamo.setCity(city);
        userTeamo.setZodiac(zodiac);
        userTeamo.setHeight(height);
        userTeamo.setGender(gender);
        userTeamo.setLikeList(likeList);
        userTeamo.setDislikeList(dislikeList);
        userTeamo.setGeneralAttributes(generalAttributeList);
        userTeamo.setPhotos(photos);
        userTeamo.setLifestyleList(lifestyleList);
        userTeamo.setUserLookings(userLookingList);

        return userTeamo;
    }

    @Override
    public List<UserTeamo> mapFromList(JsonNode jsonNode)
    {
        List<JsonNode> users = jsonNode.path("result").findValues("users");
        List<UserTeamo> usersTeamo = new ArrayList<>();
        for (JsonNode user : users.get(0))
        {
            usersTeamo.add(
                    UserTeamo.builder()
                            .id(user.path("id").asLong())
                            .name(user.path("name").asText())
                            .age(user.path("age").asInt())
                            .city(user.path("location").asText())
                            .zodiac(user.path("zodiac").asText())
                            .gender(user.path("sex").asInt() == 1 ? "male": "female")
                            .build()
            );
        }
        return usersTeamo;
    }

    @Override
    public List<UserTeamo> mapFromListMessages(JsonNode jsonNode)
    {
        List<JsonNode> users = jsonNode.path("result").findValues("messages");
        List<UserTeamo> usersTeamo = new ArrayList<>();
        for (JsonNode user : users.get(0))
        {
            usersTeamo.add(
                   mapFromJsonInList(user.path("user_info"))
            );
        }
        return usersTeamo;
    }

    private UserTeamo mapFromJsonInList(JsonNode user)
    {
        return  UserTeamo.builder()
                .id(user.path("id").asLong())
                .name(user.path("name").asText())
                .age(user.path("age").asInt())
                .city(user.path("location").asText())
                .zodiac(user.path("zodiac").asText())
                .gender(user.path("sex").asInt() == 1 ? "male": "female")
                .build();
    }
}
