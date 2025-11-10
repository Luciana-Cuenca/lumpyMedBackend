package com.medical.dosage.pediatric_dosage_calculator.dto;

public class DoseResponse {
    private String medicine;
    private double weightKg;
    private double mgPerDay;
    private int dosesPerDay;
    private double mgPerDose;
    private double mlPerDose;
    private String alert;
    private String safeRange;

    public DoseResponse(String medicine, double weightKg, double mgPerDay, int dosesPerDay,
                        double mgPerDose, double mlPerDose, String alert, String safeRange) {
        this.medicine = medicine;
        this.weightKg = weightKg;
        this.mgPerDay = mgPerDay;
        this.dosesPerDay = dosesPerDay;
        this.mgPerDose = mgPerDose;
        this.mlPerDose = mlPerDose;
        this.alert = alert;
        this.safeRange = safeRange;
    }

    // Getters
    public String getMedicine() { return medicine; }
    public double getWeightKg() { return weightKg; }
    public double getMgPerDay() { return mgPerDay; }
    public int getDosesPerDay() { return dosesPerDay; }
    public double getMgPerDose() { return mgPerDose; }
    public double getMlPerDose() { return mlPerDose; }
    public String getAlert() { return alert; }
    public String getSafeRange() { return safeRange; }
}
