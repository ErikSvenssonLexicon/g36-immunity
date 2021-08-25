package se.lexicon.immunity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.immunity.data.BookingDAO;
import se.lexicon.immunity.data.PatientDAO;
import se.lexicon.immunity.data.PremisesDAO;
import se.lexicon.immunity.exception.AppResourceNotFoundException;
import se.lexicon.immunity.model.dto.BookingDTO;
import se.lexicon.immunity.model.entity.Booking;
import se.lexicon.immunity.model.entity.Patient;
import se.lexicon.immunity.model.entity.Premises;

@Service
public class BookingServiceImpl implements BookingService{

    private final BookingDAO bookingDAO;
    private final PremisesDAO premisesDAO;
    private final PatientDAO patientDAO;
    private final DTOConverterService converterService;

    public BookingServiceImpl(BookingDAO bookingDAO, PremisesDAO premisesDAO, PatientDAO patientDAO, DTOConverterService converterService) {
        this.bookingDAO = bookingDAO;
        this.premisesDAO = premisesDAO;
        this.patientDAO = patientDAO;
        this.converterService = converterService;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BookingDTO create(BookingDTO bookingDTO, String premisesId){
        Booking booking = new Booking(
                bookingDTO.getDateTime(),
                bookingDTO.getPrice(),
                bookingDTO.getVaccineType()
        );
        Premises premises = premisesDAO.findById(premisesId)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find premise with id " + premisesId));

        booking.setPremises(premises);

        Booking persisted = bookingDAO.save(booking);
        return converterService.toFullDTO(persisted);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDTO findById(String id) {
        return bookingDAO.findById(id)
                .map(converterService::toFullDTO)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find booking with id " + id));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BookingDTO book(String bookingId, String patientId) {
        Booking booking = bookingDAO.findById(bookingId)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find booking with id " +bookingId));
        Patient patient = patientDAO.findById(patientId)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find patient with id " + patientId));

        if(!booking.isVacant() && booking.getPatient() != null){
            if(!booking.getPatient().getId().equals(patientId)){
                throw new IllegalStateException("Found booking is not vacant");
            }
        }

        booking.setPatient(patient);
        booking.setVacant(false);

        Booking updated = bookingDAO.save(booking);

        return converterService.toFullDTO(updated);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BookingDTO unBook(String bookingId, String patientId) {
        Booking booking = bookingDAO.findById(bookingId)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find booking with id " + bookingId));

        Patient patient = patientDAO.findById(patientId)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find patient with id " + patientId));

        if(booking.getPatient() != null && !booking.getPatient().getId().equals(patient.getId())){
            throw new IllegalStateException("Operation aborted: found patient.id didn't match provided id");
        }

        booking.setPatient(null);
        booking.setVacant(true);

        Booking updated = bookingDAO.save(booking);

        return converterService.toFullDTO(updated);
    }

}
