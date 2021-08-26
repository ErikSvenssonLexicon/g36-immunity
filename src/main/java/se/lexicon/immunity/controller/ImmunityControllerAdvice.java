package se.lexicon.immunity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import se.lexicon.immunity.exception.AppResourceNotFoundException;
import se.lexicon.immunity.exception.ExceptionResponse;

@ControllerAdvice
public class ImmunityControllerAdvice {

    public ExceptionResponse composeExceptionResponse(HttpStatus status, String message, String path){
        return new ExceptionResponse(
                status,
                message,
                path
        );
    }

    @ExceptionHandler(AppResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAppResourceNotFoundException(AppResourceNotFoundException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(composeExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getDescription(false)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request){
        return ResponseEntity.badRequest()
                .body(composeExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getDescription(false)));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(composeExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage(), request.getDescription(false)));
    }

}
