package se.lexicon.immunity.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidationErrorResponse extends ExceptionResponse{

    private final List<Violation> violations;

    public ValidationErrorResponse(HttpStatus status, String message, String path, List<Violation> violations){
        super(status, message, path);
        this.violations = violations;
    }

    public List<Violation> getViolations() {
        return violations;
    }
}
