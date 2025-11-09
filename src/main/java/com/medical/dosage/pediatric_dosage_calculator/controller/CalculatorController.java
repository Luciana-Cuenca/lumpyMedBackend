package com.medical.dosage.pediatric_dosage_calculator.controller;

import com.medical.dosage.pediatric_dosage_calculator.dto.DoseRequest;
import com.medical.dosage.pediatric_dosage_calculator.dto.DoseResponse;
import com.medical.dosage.pediatric_dosage_calculator.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin("*")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/dose")
    public ResponseEntity<?> calculate(@RequestBody DoseRequest request) {
        try {
            // Pasamos tanto años como meses
            DoseResponse response = calculatorService.calculateDose(
                    request.getMedicineName(),
                    request.getWeightKg(),
                    request.getAgeYears(),
                    request.getAgeMonths()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
}

}
