package se.lexicon.immunity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/api/v1/bookings/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable("id") String bookingId){
        return ResponseEntity.ok(bookingService.findById(bookingId));
    }

    @PutMapping("/api/v1/bookings/{id}/book")
    public ResponseEntity<BookingDTO> bookVaccine(
            @PathVariable("id") String bookingId,
            @RequestParam(name = "patientId") String patientId){
        return ResponseEntity.ok(bookingService.book(bookingId, patientId));
    }
}
