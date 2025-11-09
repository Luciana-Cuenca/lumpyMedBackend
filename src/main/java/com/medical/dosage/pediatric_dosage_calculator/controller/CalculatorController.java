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
    public ResponseEntity<?> calculateDose(@RequestBody Map<String, Object> payload) {

        try {
            String medicineName = payload.get("medicineName").toString();
            Double weightKg = Double.valueOf(payload.get("weightKg").toString());

            var medicineOpt = medicineRepository.findByName(medicineName);
            if (medicineOpt.isEmpty()) return ResponseEntity.badRequest().body("Medicamento no encontrado");

            var medicine = medicineOpt.get();

            // mg máximo por día
            Double maxMgDay = medicine.getDosageMax() * weightKg;

            // extraer concentración -> "250mg/5ml"
            String c = medicine.getConcentration().toLowerCase().replace(" ", "");
            String[] parts = c.split("mg/");
            double mg = Double.valueOf(parts[0]);
            double ml = Double.valueOf(parts[1].replace("ml", ""));
            double mgPorMl = mg / ml;

            double maxPerDose = medicine.getMaxDosePerDose(); // mg máx por toma

            List<Map<String, Object>> opciones = new ArrayList<>();

            int[] intervalos = {12, 8, 6};

            for (int horas : intervalos) {
                int tomasDia = 24 / horas;
                double mgPorToma = maxMgDay / tomasDia;

                if (mgPorToma <= maxPerDose) {
                    double mlPorToma = mgPorToma / mgPorMl;

                    opciones.add(Map.of(
                            "cadaHoras", horas,
                            "tomasPorDia", tomasDia,
                            "mgPorToma", mgPorToma,
                            "mlPorToma", mlPorToma
                    ));
                }
            }

            return ResponseEntity.ok(Map.of(
                    "medicine", medicineName,
                    "weightKg", weightKg,
                    "maxMgDay", maxMgDay,
                    "opcionesSeguras", opciones
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
