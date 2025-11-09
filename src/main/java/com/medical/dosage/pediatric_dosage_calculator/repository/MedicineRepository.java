package com.medical.dosage.pediatric_dosage_calculator.repository;

import com.medical.dosage.pediatric_dosage_calculator.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

        Optional<Medicine> findByName(String name);

    
}
