package com.plantations.management.system.PMS.Repository;

import com.plantations.management.system.PMS.Model.Crops;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropsRepo extends JpaRepository<Crops,Integer> {
    Page<Crops> findAll(Pageable pageable);
    Page<Crops> findByCropNameContainingIgnoreCase(String cropName, Pageable pageable);
}
