package se.lexicon.immunity.exception;

public class AppResourceNotFoundException extends RuntimeException{
    public AppResourceNotFoundException(String message) {
        super(message);
    }
}
