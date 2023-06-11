package ru.itgroup.intouch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itgroup.intouch.client.exceptionHandling.ErrorResponse;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.NoEmailFoundException;
import ru.itgroup.intouch.exceptions.NoUserRegisteredException;
import ru.itgroup.intouch.exceptions.UserAlreadyRegisteredException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({CaptchaNotValidException.class,
            UserAlreadyRegisteredException.class,
            NoEmailFoundException.class,
            UserAlreadyRegisteredException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception,
                                                          WebRequest request) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                exception.getMessage(),
                request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoUserRegisteredException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorized(Exception exception,
                                                            WebRequest request) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.name(),
                exception.getMessage(),
                request.getDescription(false)),
                HttpStatus.UNAUTHORIZED);
    }


}
