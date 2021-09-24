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

    @GetMapping("/api/v1/bookings")
    public ResponseEntity<?> search(@RequestParam(name = "search", defaultValue = "all") String search, @RequestParam(name = "value", defaultValue = "") String value){
        switch (search){
            case "all":
                return ResponseEntity.ok(bookingService.findAllByVacantStatus(true));
            case "city":
                return ResponseEntity.ok(bookingService.findAllByCityAnVacantStatus(value, true));
            default:
                throw new IllegalArgumentException("Invalid param " + search + " was expecting 'all' or 'city'");
        }
    }

    @PostMapping("/api/v1/bookings")
    public ResponseEntity<BookingDTO> create(@RequestBody BookingDTO newBooking, @RequestParam(name = "id") String premiseId){
        return ResponseEntity.status(201).body(bookingService.create(newBooking, premiseId));
    }

    @GetMapping("/api/v1/bookings/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable("id") String bookingId){
        return ResponseEntity.ok(bookingService.findById(bookingId));
    }

    @PutMapping("/api/v1/bookings/{id}/people")
    public ResponseEntity<BookingDTO> bookVaccine(
            @PathVariable("id") String bookingId,
            @RequestParam(name = "patientId") String patientId,
            @RequestParam(name = "isBooking", defaultValue = "true") Boolean isBooking){
        if(isBooking){
            return ResponseEntity.ok(bookingService.book(bookingId, patientId));
        }

        return ResponseEntity.ok(bookingService.unBook(bookingId, patientId));
    }

    @PutMapping("/api/v1/bookings/{id}")
    public ResponseEntity<BookingDTO> update(@PathVariable("id") String id, @RequestBody BookingDTO bookingDTO){
        return ResponseEntity.ok(bookingService.update(id, bookingDTO));
    }


}
