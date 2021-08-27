package se.lexicon.immunity.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniqueEmailImpl.class})
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email is already taken.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
