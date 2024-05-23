package app.redqueen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatisticsTeamo
{
    private Long dialogsNew;
    private Long dialogsAllNew;
    private Long dialogsMutualNew;
    private Long notificationsNew;
    private Long notificationsNewV2;
    private Long guestsNew;
    private Long facesIncomeNew;
    private Long commentsNew;
    private Long inFavouritesNew;
    private Long psychoSelection;
}
