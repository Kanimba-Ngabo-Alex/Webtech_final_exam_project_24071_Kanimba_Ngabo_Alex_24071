package com.plantations.management.system.PMS.Repository;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Plantations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantationsRepo extends JpaRepository<Plantations,Integer> {
    List<Plantations> findByOwnerId(Owners owners);
    List<Plantations> findByCropId(Crops crops);
    Page<Plantations> findAll(Pageable pageable);
    Page<Plantations> findByLocationContainingIgnoreCase(String location, Pageable pageable);
}
