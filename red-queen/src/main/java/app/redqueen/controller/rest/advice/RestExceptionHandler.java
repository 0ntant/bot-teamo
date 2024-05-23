package app.redqueen.controller.rest.advice;

import app.redqueen.dto.output.error.ApiError;
import app.redqueen.exception.ElementAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler
{
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(
            NoSuchElementException ex ,
            WebRequest request
    )
    {
        String path = request.getDescription(false).substring(4);
        log.error("{}", ex.getMessage());
        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .path(path)
                .build();
        return ResponseEntity
                .status(error.getStatus())
                .body(error);
    }

    @ExceptionHandler(ElementAlreadyExistsException.class)
    public ResponseEntity<Object> handleElementAlreadyExistsException(
            ElementAlreadyExistsException ex,
            WebRequest request
    )
    {
        String path = request.getDescription(false).substring(4);
        log.error("{}", ex.getMessage());
        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .path(path)
                .build();
        return ResponseEntity
                .status(error.getStatus())
                .body(error);
    }
}
