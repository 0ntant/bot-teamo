package app.redqueen.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BotUserData
{
    long userId;
    String name;
    String city;
    Date sysCreateDate;
    String state;
    StatisticsTeamo statisticsTeamo;
}
