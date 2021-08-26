package se.lexicon.immunity.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private final LocalDateTime timeStamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;

    public ExceptionResponse(LocalDateTime timeStamp, Integer status, String error, String message, String path) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ExceptionResponse(HttpStatus status, String message, String path){
        this(LocalDateTime.now(), status.value(), status.name(), message, path);
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
