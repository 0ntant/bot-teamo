package app.service.exception;

public class EntityAlreadyExists extends RuntimeException
{
    public EntityAlreadyExists(String message)
    {
        super(message);
    }

    public EntityAlreadyExists(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
