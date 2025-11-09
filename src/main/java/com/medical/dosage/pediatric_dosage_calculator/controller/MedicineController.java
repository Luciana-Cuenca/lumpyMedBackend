package com.medical.dosage.pediatric_dosage_calculator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dosage.pediatric_dosage_calculator.model.Medicine;
import com.medical.dosage.pediatric_dosage_calculator.repository.MedicineRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineRepository medicineRepository;

    public MedicineController(MedicineRepository medicineRepository){
        this.medicineRepository = medicineRepository;
    }

    @GetMapping
    public List<Medicine> getAll() {
        return medicineRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getById(@PathVariable Long id){
        return medicineRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Medicine create(@RequestBody Medicine medicine){
        return medicineRepository.save(medicine);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Medicine> update(@PathVariable Long id, @RequestBody Medicine medicine){
        return medicineRepository.findById(id).map(m -> {
            m.setName(medicine.getName());
            m.setDescription(medicine.getDescription());
            m.setDosageMin(medicine.getDosageMin());
            m.setDosageMax(medicine.getDosageMax());
            m.setConcentration(medicine.getConcentration());
            m.setMaxDosePerDose(medicine.getMaxDosePerDose()); // ← ESTA LINEA IMPORTANTE
            return ResponseEntity.ok(medicineRepository.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!medicineRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        medicineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
