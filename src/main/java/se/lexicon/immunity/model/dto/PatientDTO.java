package se.lexicon.immunity.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;
import se.lexicon.immunity.validators.OnCreate;
import se.lexicon.immunity.model.demo.Gender;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static se.lexicon.immunity.util.ValidationMessages.PAST_OR_PRESENT;
import static se.lexicon.immunity.util.ValidationMessages.REQUIRED_FIELD;

@Validated
public class PatientDTO implements Serializable {
    @Null(groups = OnCreate.class)
    private String id;

    @NotBlank(message = REQUIRED_FIELD)
    private String pnr;
    @NotBlank(message = REQUIRED_FIELD)
    private String firstName;
    @NotBlank(message = REQUIRED_FIELD)
    private String lastName;
    @NotNull(message = REQUIRED_FIELD)
    @PastOrPresent(message = PAST_OR_PRESENT)
    private LocalDate birthDate;
    @NotNull(message = REQUIRED_FIELD)
    private Gender gender;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Valid
    private ContactInfoDTO contactInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BookingDTO> bookings;

    public PatientDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ContactInfoDTO getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoDTO contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }
}
