package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.Lifestyle;
import app.redqueen.model.UserTeamo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class UserTeamoFullInfoDto
{
    Long id;
    String name;
    Integer age;
    String city;
    List<GeneralAttributeDto> generalAttributeList;
    List<LifestyleDto> lifestyleList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime lastVisit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime sysCreateDate;

    Integer height;
    String gender;
    BlockDto block;

    List<PhotoDto> photoList;
    List<LikeDto> likeList;
    List<DislikeDto> dislikeList;
    List<UserLookingDto> lookingList;
    List<UserInBlackListDto> blacklist;

    public static UserTeamoFullInfoDto mapToUserTeamoFullInfoDto(UserTeamo userTeamo)
    {
        return UserTeamoFullInfoDto.builder()
                .id(userTeamo.getId())
                .name(userTeamo.getName())
                .age(userTeamo.getAge())
                .generalAttributeList(
                        userTeamo.getGeneralAttributes()
                                .stream()
                                .map(GeneralAttributeDto::mapToGeneralAttributeDto)
                                .toList()
                )
                .lifestyleList(
                        userTeamo.getLifestyleList()
                                .stream()
                                .map(LifestyleDto::mapToLifestyleDto)
                                .toList()
                )
                .lastVisit(
                        userTeamo.
                                getLastVisit()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                )
                .sysCreateDate(
                        userTeamo.
                            getSysCreateDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                )
                .city(userTeamo.getCity())
                .height(userTeamo.getHeight())
                .gender(userTeamo.getGender())
                .block(BlockDto.mapToBlockDto(userTeamo.getBlock()))
                .photoList(
                        userTeamo.getPhotos()
                                .stream()
                                .map(PhotoDto::mapToPhotoDto)
                                .toList()
                )
                .likeList(
                        userTeamo.getLikeList()
                                .stream()
                                .map(LikeDto::mapToLikeDto)
                                .toList()
                )
                .dislikeList(
                        userTeamo.getDislikeList()
                                .stream()
                                .map(DislikeDto::mapToDislikeDto)
                                .toList()
                )
                .lookingList(
                        userTeamo.getUserLookings()
                                .stream()
                                .map(UserLookingDto::mapToUserLookingDto)
                                .toList()
                )
                .blacklist(
                        userTeamo.getBlackList()
                                .stream()
                                .map(UserInBlackListDto::mapToUserInBlackListDto)
                                .toList()
                )
                .build();
    }
}
