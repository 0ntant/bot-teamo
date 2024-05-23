package app.redqueen.dto.output.UserTeamoFullInfo;

import app.redqueen.model.UserTeamoBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
public class BlockDto
{
    Boolean isBlocked;
    String reason;
    Integer teamoCode;
    Date blockCheckDate;

    static BlockDto mapToBlockDto(UserTeamoBlock userTeamoBlock)
    {
        return BlockDto.builder()
                .isBlocked(userTeamoBlock.getIsBlocking())
                .reason(userTeamoBlock.getReason())
                .teamoCode(userTeamoBlock.getTeamoCode())
                .blockCheckDate(userTeamoBlock.getBlockDate())
                .build();
    }
}
