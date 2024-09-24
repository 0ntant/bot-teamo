package app.service.dto.output;

import app.service.model.ImageAvatar;
import app.service.util.Base64Util;
import app.service.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ImgDto
{
    String name;
    String gender;
    String base64;

    public static ImgDto imageAvatarToImgDto(ImageAvatar imageAvatar)
    {
        return ImgDto.builder()
                .name(FileUtil.getName(imageAvatar.getPath()))
                .gender(imageAvatar.getGender())
                .base64(
                        new String(
                                Base64Util.encode(
                                        FileUtil.getBytes(
                                                imageAvatar.getPath()
                                        )
                                )
                        )
                )
                .build();
    }
}
