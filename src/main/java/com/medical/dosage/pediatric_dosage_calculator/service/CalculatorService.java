package com.medical.dosage.pediatric_dosage_calculator.service;

import com.medical.dosage.pediatric_dosage_calculator.dto.DoseResponse;
import com.medical.dosage.pediatric_dosage_calculator.model.Medicine;
import com.medical.dosage.pediatric_dosage_calculator.repository.MedicineRepository;
import com.medical.dosage.pediatric_dosage_calculator.strategy.DoseCalculatorStrategy;
import com.medical.dosage.pediatric_dosage_calculator.strategy.WeightBasedDoseCalculator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalculatorService {

    private final MedicineRepository medicineRepository;
    private final DoseCalculatorStrategy calculatorStrategy;

    public CalculatorService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
        this.calculatorStrategy = new WeightBasedDoseCalculator();
    }

    public DoseResponse calculateDose(String medicineName, double weightKg) throws Exception {
        Optional<Medicine> medicineOpt = medicineRepository.findByName(medicineName);
        if (medicineOpt.isEmpty())
            throw new Exception("Medicine not found");

        Medicine m = medicineOpt.get();

        double mgPerDay = calculatorStrategy.calculateMgPerDay(m, weightKg);
        double mgPerDose = mgPerDay / m.getDosesPerDay();
        double mgPerMl = m.getConcentrationMg() / m.getConcentrationMl();
        double mlPerDose = mgPerDose / mgPerMl;

        return new DoseResponse(
                medicineName,
                weightKg,
                round(mgPerDay, 2),
                m.getDosesPerDay(),
                round(mgPerDose, 2),
                round(mlPerDose, 2)
        );
    }

    private static double round(double v, int places) {
        double f = Math.pow(10, places);
        return Math.round(v * f) / f;
    }
}
