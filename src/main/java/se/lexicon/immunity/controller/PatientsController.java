package se.lexicon.immunity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.immunity.validators.OnCreate;
import se.lexicon.immunity.model.dto.PatientDTO;
import se.lexicon.immunity.service.PatientService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
public class PatientsController {

    private final PatientService patientService;

    public PatientsController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/api/v1/patients")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO){
        System.out.println(patientDTO);

        return ResponseEntity.status(201).body(patientService.create(patientDTO));
    }

    @GetMapping("/api/v1/patients")
    public ResponseEntity<?> search(@RequestParam(name = "pnr", required = false) String pnr){
        if(pnr != null){
            return ResponseEntity.ok(patientService.findByPersonalNumber(pnr));
        }
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
