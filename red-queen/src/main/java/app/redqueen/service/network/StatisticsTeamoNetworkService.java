package app.redqueen.service.network;

import app.redqueen.model.StatisticsTeamo;

public interface StatisticsTeamoNetworkService extends GeneralNetworkService
{
    ResultOrError<StatisticsTeamo> getStatistic();
}
