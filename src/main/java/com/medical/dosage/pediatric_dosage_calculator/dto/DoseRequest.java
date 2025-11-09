package com.medical.dosage.pediatric_dosage_calculator.dto;

public class DoseRequest {
    private String medicineName;
    private double weightkg;

    //Getters y swetter
    public String getMedicineName() {return medicineName;}
    public void setMedicineName(String medicineName) {this.medicineName = medicineName;}

    public double getWeightKg() {return weightkg;}
    public void setWeightKg(double weightKg) {this.weightkg = weightKg;}

}
