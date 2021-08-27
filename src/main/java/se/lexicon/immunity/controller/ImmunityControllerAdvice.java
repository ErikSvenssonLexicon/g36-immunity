package se.lexicon.immunity.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import se.lexicon.immunity.exception.AppResourceNotFoundException;
import se.lexicon.immunity.exception.ExceptionResponse;
import se.lexicon.immunity.exception.ValidationErrorResponse;
import se.lexicon.immunity.exception.Violation;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ImmunityControllerAdvice {

    public static final String VALIDATION_MESSAGE = "One or several validations failed";

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request){
        List<Violation> violations = new ArrayList<>();
        for(FieldError error : exception.getFieldErrors()){
            violations.add(new Violation(error.getField(), error.getDefaultMessage()));
        }
        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST,
                VALIDATION_MESSAGE,
                request.getDescription(false),
                violations
        );

        return ResponseEntity.badRequest().body(response);
    }

}
