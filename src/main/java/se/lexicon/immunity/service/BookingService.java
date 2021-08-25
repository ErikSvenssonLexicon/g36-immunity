package se.lexicon.immunity.service;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.immunity.model.dto.BookingDTO;

public interface BookingService {
    @Transactional(rollbackFor = RuntimeException.class)
    BookingDTO create(BookingDTO bookingDTO, String premisesId);
    BookingDTO findById(String id);
    BookingDTO book(String bookingId, String patientId);
}
