package app.redqueen.service.network;

import app.redqueen.model.UserTeamoBlock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultOrError<T>
{
    private T result ;
    private UserTeamoBlock block;

    public static <R>ResultOrError <R>successResponse(R result)
    {
        return new ResultOrError<R> (result, null);
    }

    public static <R>ResultOrError <R>errorResponse(UserTeamoBlock block)
    {
        return new ResultOrError<> (null, block);
    }

    public boolean isSuccessResponse()
    {
        return result != null;
    }

    public boolean isErrorResponse()
    {
        return !isSuccessResponse();
    }
}
