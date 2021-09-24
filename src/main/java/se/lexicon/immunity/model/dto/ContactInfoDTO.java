package se.lexicon.immunity.model.dto;

import org.springframework.validation.annotation.Validated;
import se.lexicon.immunity.validators.OnCreate;
import se.lexicon.immunity.validators.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static se.lexicon.immunity.util.Regexes.EMAIL_REGEX;
import static se.lexicon.immunity.util.ValidationMessages.*;


public class ContactInfoDTO implements Serializable {

    private String id;
    @NotBlank(message = REQUIRED_FIELD)
    @Email(message = INVALID_EMAIL_FORMAT, regexp = EMAIL_REGEX)
    @UniqueEmail()
    private String email;
    private String phone;

    public ContactInfoDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
