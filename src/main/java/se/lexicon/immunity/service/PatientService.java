package se.lexicon.immunity.service;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.immunity.model.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    @Transactional(rollbackFor = RuntimeException.class)
    PatientDTO create(PatientDTO patientDTO);

    @Transactional(readOnly = true)
    List<PatientDTO> findAll();

    PatientDTO findById(String id);

    PatientDTO update(String id, PatientDTO patientDTO);

    PatientDTO findByPersonalNumber(String pnr);
}
