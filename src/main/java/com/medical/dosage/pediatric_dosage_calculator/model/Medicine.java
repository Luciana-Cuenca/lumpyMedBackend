package com.medical.dosage.pediatric_dosage_calculator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    // mg/kg/dosis pediatrica recomendada
    @Column(nullable = false)
    private Double dosageMin; // mg/kg
    @Column(nullable = false)
    private Double dosageMax; // mg/kg
    private Double maxDosePerDose;


    // concentración estandar 
    @Column(nullable = false)
     private String concentration;// mg por ml

    // GETTERS & SETTERS
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getDosageMin() { return dosageMin; }
    public void setDosageMin(Double dosageMin) { this.dosageMin = dosageMin; }

    public Double getDosageMax() { return dosageMax; }
    public void setDosageMax(Double dosageMax) { this.dosageMax = dosageMax; }

    public String getConcentration() { return concentration;}
    public void setConcentration(String concentration) { this.concentration = concentration; }

    public Double getMaxDosePerDose() {return  maxDosePerDose; }
    public void setMaxDosePerDose(Double maxDosePerDose) {this.maxDosePerDose = maxDosePerDose; }


}
