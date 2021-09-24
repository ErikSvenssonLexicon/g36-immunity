package se.lexicon.immunity.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.immunity.model.entity.Patient;

import java.util.Optional;

public interface PatientDAO extends JpaRepository<Patient, String> {
    @Query("SELECT p FROM Patient p WHERE p.pnr = :pnr")
    Optional<Patient> findByPnr(@Param("pnr") String pnr);
}
