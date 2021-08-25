package se.lexicon.immunity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.immunity.data.BookingDAO;
import se.lexicon.immunity.data.PremisesDAO;
import se.lexicon.immunity.exception.AppResourceNotFoundException;
import se.lexicon.immunity.model.dto.BookingDTO;
import se.lexicon.immunity.model.entity.Booking;
import se.lexicon.immunity.model.entity.Premises;

@Service
public class BookingServiceImpl implements BookingService{

    private final BookingDAO bookingDAO;
    private final PremisesDAO premisesDAO;
    private final DTOConverterService converterService;

    public BookingServiceImpl(BookingDAO bookingDAO, PremisesDAO premisesDAO, DTOConverterService converterService) {
        this.bookingDAO = bookingDAO;
        this.premisesDAO = premisesDAO;
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

}
