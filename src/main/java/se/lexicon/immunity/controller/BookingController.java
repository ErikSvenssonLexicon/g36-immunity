package se.lexicon.immunity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.immunity.model.dto.BookingDTO;
import se.lexicon.immunity.service.BookingService;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/api/v1/bookings")
    public ResponseEntity<BookingDTO> create(@RequestBody BookingDTO newBooking, @RequestParam(name = "id") String premiseId){
        return ResponseEntity.status(201).body(bookingService.create(newBooking, premiseId));
    }

}
