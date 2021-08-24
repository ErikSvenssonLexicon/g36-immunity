package se.lexicon.immunity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.immunity.model.dto.PatientDTO;
import se.lexicon.immunity.service.PatientService;

import java.util.List;

@RestController
public class PatientsController {

    private final PatientService patientService;

    public PatientsController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/api/v1/patients")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.status(201).body(patientService.create(patientDTO));
    }

    @GetMapping("/api/v1/patients")
    public ResponseEntity<List<PatientDTO>> search(){
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/api/v1/patients/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable(name = "id") String id){
        return ResponseEntity.ok(patientService.findById(id));
    }

    @PutMapping("/api/v1/patients/{id}")
    public ResponseEntity<PatientDTO> update(@PathVariable(name = "id") String id, @RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(patientService.update(id, patientDTO));
    }
}
