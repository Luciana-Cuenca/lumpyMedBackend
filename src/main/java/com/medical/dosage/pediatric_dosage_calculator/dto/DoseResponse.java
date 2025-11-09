package com.medical.dosage.pediatric_dosage_calculator.dto;

public class DoseResponse {
    private String medicine;
    private double weightKg;
    private int ageMonths;         // Edad en meses
    private double mgPerDay;
    private int dosesPerDay;
    private double mgPerDose;
    private double mlPerDose;
    private String alert;          // Alertas AIEPI
    private String safeRange;      // Rango seguro en ml

    // Constructor
    public DoseResponse(String medicine, double weightKg, int ageMonths, double mgPerDay,int dosesPerDay, double mgPerDose, double mlPerDose,String alert, String safeRange) 
    {
        this.medicine = medicine;
        this.weightKg = weightKg;
        this.ageMonths = ageMonths;
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
    public int getAgeMonths() { return ageMonths; }
    public double getMgPerDay() { return mgPerDay; }
    public int getDosesPerDay() { return dosesPerDay; }
    public double getMgPerDose() { return mgPerDose; }
    public double getMlPerDose() { return mlPerDose; }
    public String getAlert() { return alert; }
    public String getSafeRange() { return safeRange; }
}
