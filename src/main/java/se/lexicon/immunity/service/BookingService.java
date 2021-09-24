package se.lexicon.immunity.service;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.immunity.model.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    @Transactional(rollbackFor = RuntimeException.class)
    BookingDTO create(BookingDTO bookingDTO, String premisesId);
    BookingDTO findById(String id);
    List<BookingDTO> findAllByVacantStatus(boolean vacantStatus);
    List<BookingDTO> findAllByCityAnVacantStatus(String city, boolean vacantStatus);
    BookingDTO book(String bookingId, String patientId);
    BookingDTO unBook(String bookingId, String patientId);
    BookingDTO update(String id, BookingDTO bookingDTO);
}
