package se.lexicon.immunity.validators;

import org.springframework.stereotype.Component;
import se.lexicon.immunity.data.ContactInfoDAO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailImpl implements ConstraintValidator<UniqueEmail, String> {

    private final ContactInfoDAO contactInfoDAO;

    public UniqueEmailImpl(ContactInfoDAO contactInfoDAO) {
        this.contactInfoDAO = contactInfoDAO;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !contactInfoDAO.findByEmailIgnoreCase(email).isPresent();
    }
}
