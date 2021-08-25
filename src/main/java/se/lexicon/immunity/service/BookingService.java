package se.lexicon.immunity.service;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.immunity.model.dto.BookingDTO;

public interface BookingService {
    @Transactional(rollbackFor = RuntimeException.class)
    BookingDTO create(BookingDTO bookingDTO, String premisesId);
}
