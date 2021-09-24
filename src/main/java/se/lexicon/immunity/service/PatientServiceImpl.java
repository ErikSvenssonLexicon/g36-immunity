package se.lexicon.immunity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.immunity.data.BookingDAO;
import se.lexicon.immunity.data.PatientDAO;
import se.lexicon.immunity.exception.AppResourceNotFoundException;
import se.lexicon.immunity.model.dto.PatientDTO;
import se.lexicon.immunity.model.entity.Booking;
import se.lexicon.immunity.model.entity.ContactInfo;
import se.lexicon.immunity.model.entity.Patient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService{

    private final PatientDAO patientDAO;
    private final BookingDAO bookingDAO;
    private final DTOConverterService converterService;

    public PatientServiceImpl(PatientDAO patientDAO, BookingDAO bookingDAO, DTOConverterService converterService) {
        this.patientDAO = patientDAO;
        this.bookingDAO = bookingDAO;
        this.converterService = converterService;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public PatientDTO create(PatientDTO patientDTO){
        String pnr = patientDTO.getPnr().replaceAll("-", "").replaceAll(" ", "");
        Optional<Patient> optional = patientDAO.findByPnr(pnr);
        if(optional.isPresent()){
            return converterService.toFullDTO(optional.get(), bookingDAO.findByPatientId(optional.get().getId()));
        }else{
            Patient patient = new Patient(
                    pnr,
                    patientDTO.getFirstName(),
                    patientDTO.getLastName(),
                    patientDTO.getBirthDate(),
                    patientDTO.getGender()
            );

            if(patientDTO.getContactInfo() != null){
                ContactInfo contactInfo = new ContactInfo(
                        patientDTO.getContactInfo().getEmail(),
                        patientDTO.getContactInfo().getPhone()
                );
                patient.setContactInfo(contactInfo);
            }

            Patient persisted = patientDAO.save(patient);

            return converterService.toFullDTO(persisted, null);
        }
    }

    @Transactional(readOnly = true)
    public Patient internalFindById(String id){
        return patientDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find patient with id " + id));
    }

    @Transactional(readOnly = true)
    public Patient internalFindByPersonalNumber(String personalNumber){
        String pnr = personalNumber.replaceAll("-","").trim();
        return patientDAO.findByPnr(pnr)
                .orElseThrow(() -> new AppResourceNotFoundException("No patient with personal number " + personalNumber + " was found in the database"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDTO> findAll(){
        return patientDAO.findAll().stream()
                .map(converterService::toSmallDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDTO findById(String id) {
        Patient patient = internalFindById(id);
        List<Booking> bookings = bookingDAO.findByPatientId(id);
        return converterService.toFullDTO(patient, bookings);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public PatientDTO update(String id, PatientDTO patientDTO) {
        Patient patient = internalFindById(id);

        patient.setPnr(patientDTO.getPnr().replaceAll("-", "").replaceAll(" ", "").trim());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setGender(patientDTO.getGender());
        patient.getContactInfo().setEmail(patientDTO.getContactInfo().getEmail());
        patient.getContactInfo().setPhone(patientDTO.getContactInfo().getPhone());

        patient = patientDAO.save(patient);
        return converterService.toFullDTO(patient, bookingDAO.findByPatientId(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDTO findByPersonalNumber(String pnr) {
        Patient patient = internalFindByPersonalNumber(pnr);
        return converterService.toFullDTO(patient, bookingDAO.findByPatientId(patient.getId()));
    }
}
