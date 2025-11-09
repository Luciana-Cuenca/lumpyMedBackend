package com.medical.dosage.pediatric_dosage_calculator.strategy;

import com.medical.dosage.pediatric_dosage_calculator.model.Medicine;

public interface DoseCalculatorStrategy {
    double calculateMgPerDay(Medicine medicine, double weightKg);
}
