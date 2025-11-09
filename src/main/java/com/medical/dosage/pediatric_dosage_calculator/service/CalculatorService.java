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

    public DoseResponse calculateDose(String medicineName, double weightKg, int ageYears, int ageMonths) throws Exception {
        Optional<Medicine> medicineOpt = medicineRepository.findByName(medicineName);
        if (medicineOpt.isEmpty())
            throw new Exception("Medicine not found");

        Medicine m = medicineOpt.get();

        int totalAgeMonths = ageYears * 12 + ageMonths;

        // Validacion de edad AIEPI
        String alert = "";
        if (m.getMinAgeMonths() != null && totalAgeMonths < m.getMinAgeMonths()) {
            alert += "Paciente demasiado joven. ";
        }
        if (m.getMaxAgeMonths() != null && totalAgeMonths > m.getMaxAgeMonths()) {
            alert += "Paciente demasiado mayor. ";
        }

        double mgPerDay = calculatorStrategy.calculateMgPerDay(m, weightKg);
        double mgPerDose = mgPerDay / m.getDosesPerDay();
        double mgPerMl = m.getConcentrationMg() / m.getConcentrationMl();
        double mlPerDose = mgPerDose / mgPerMl;

        // Alertas de dosis segura
        if (mlPerDose < m.getMinSafeMl()) alert += "Dosis por debajo del rango seguro. ";
        if (mlPerDose > m.getMaxSafeMl()) alert += "Dosis por encima del rango seguro. ";

        String safeRange = m.getMinSafeMl() + " - " + m.getMaxSafeMl() + " ml";

        return new DoseResponse(
                medicineName,
                weightKg,
                totalAgeMonths,
                round(mgPerDay, 2),
                m.getDosesPerDay(),
                round(mgPerDose, 2),
                round(mlPerDose, 2),
                alert.isEmpty() ? "Dentro del rango seguro" : alert.trim(),
                safeRange
        );
    }

    private static double round(double v, int places) {
        double f = Math.pow(10, places);
        return Math.round(v * f) / f;
    }
}
