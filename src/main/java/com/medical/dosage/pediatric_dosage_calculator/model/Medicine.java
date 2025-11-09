package com.medical.dosage.pediatric_dosage_calculator.model;

import jakarta.persistence.*;

@Entity
@Table(name ="medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    // mg/kg/día recomendado (AIEPI)
    @Column(nullable = false)
    private Double mgKgDay;

    // cuántas veces al día se administra (2,3,4)
    @Column(nullable = false)
    private Integer dosesPerDay;

    // concentración del frasco comercial
    @Column(nullable = false)
    private Double concentrationMg; // mg

    @Column(nullable = false)
    private Double concentrationMl; // ml

    // getters & setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getMgKgDay() { return mgKgDay; }
    public void setMgKgDay(Double mgKgDay) { this.mgKgDay = mgKgDay; }

    public Integer getDosesPerDay() { return dosesPerDay; }
    public void setDosesPerDay(Integer dosesPerDay) { this.dosesPerDay = dosesPerDay; }

    public Double getConcentrationMg() { return concentrationMg; }
    public void setConcentrationMg(Double concentrationMg) { this.concentrationMg = concentrationMg; }

    public Double getConcentrationMl() { return concentrationMl; }
    public void setConcentrationMl(Double concentrationMl) { this.concentrationMl = concentrationMl; }
}
