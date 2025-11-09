package com.medical.dosage.pediatric_dosage_calculator.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.medical.dosage.pediatric_dosage_calculator.repository.MedicineRepository;

import java.util.*;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin("*")
public class CalculatorController {

    private final MedicineRepository medicineRepository;

    public CalculatorController(MedicineRepository medicineRepository){
        this.medicineRepository = medicineRepository;
    }

    @PostMapping("/dose")
    public ResponseEntity<?> calculate(@RequestBody Map<String, Object> payload) {

        try {
            String medicineName = payload.get("medicineName").toString();
            Double weightKg = Double.valueOf(payload.get("weightKg").toString());

            var medicineOpt = medicineRepository.findByName(medicineName);
            if (medicineOpt.isEmpty())
                return ResponseEntity.badRequest().body("Medicine not found");

            var m = medicineOpt.get();

            double mgPerDay = m.getMgKgDay() * weightKg;
            double mgPerDose = mgPerDay / m.getDosesPerDay();
            double mgPerMl = m.getConcentrationMg() / m.getConcentrationMl();
            double mlPerDose = mgPerDose / mgPerMl;
            
            return ResponseEntity.ok(Map.of(
                    "medicine", medicineName,
                    "weightKg", weightKg,
                    "mgPerDay", round(mgPerDay,2),
                    "dosesPerDay", m.getDosesPerDay(),
                    "mgPerDose", round(mgPerDose,2),
                    "mlPerDose", round(mlPerDose,2)
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    private static double round(double v, int places) {
        double f = Math.pow(10, places);
        return Math.round(v * f) / f;
    }
}
