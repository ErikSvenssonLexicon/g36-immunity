package se.lexicon.immunity.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.immunity.model.entity.Booking;

import java.util.List;

public interface BookingDAO extends JpaRepository<Booking, String> {
    @Query("SELECT b FROM Booking b WHERE b.patient.id = :id")
    List<Booking> findByPatientId(@Param("id") String id);
    @Query("SELECT b FROM Booking b WHERE b.premises.id = :id")
    List<Booking> findByPremisesId(@Param("id") String id);
}
