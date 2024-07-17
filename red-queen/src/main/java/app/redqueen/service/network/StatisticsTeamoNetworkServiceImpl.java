package app.redqueen.service.network;

import app.redqueen.integration.rest.teamo.TeamoClient;
import app.redqueen.integration.rest.teamo.TeamoClientAPI;
import app.redqueen.mapper.api.JsonNodeToStatisticsTeamo;
import app.redqueen.mapper.api.StatisticsTeamoMapper;
import app.redqueen.model.StatisticsTeamo;
import app.redqueen.model.UserTeamo;
import app.redqueen.model.UserTeamoBlock;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsTeamoNetworkServiceImpl
        extends GeneralNetworkServiceImpl implements StatisticsTeamoNetworkService
{
    private JsonNodeToStatisticsTeamo statisticMapper;
    private TeamoClient teamoClient;

    public StatisticsTeamoNetworkServiceImpl(UserTeamo client)
    {
        super(client);
        teamoClient = new TeamoClientAPI(client.getToken());
        statisticMapper = new StatisticsTeamoMapper();
    }

    @Override
    public ResultOrError<StatisticsTeamo> getStatistic()
    {
        StatisticsTeamo statisticsTeamo;
        JsonNode apiResponse;
        try
        {
            apiResponse = teamoClient.getStatistic();
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return ResultOrError.errorResponse(UserTeamoBlock.builder()
                    .isBlocking(false)
                    .reason(ex.getMessage())
                    .build());

        }
        Optional<UserTeamoBlock> userTeamoBlock = checkResponseBlock(apiResponse);
        if(userTeamoBlock.isPresent())
        {
            log.warn("Block response to get statistic");
            return ResultOrError.errorResponse(userTeamoBlock.get());
        }
        statisticsTeamo = statisticMapper.map(apiResponse);
        return ResultOrError.successResponse(statisticsTeamo);
    }
}
