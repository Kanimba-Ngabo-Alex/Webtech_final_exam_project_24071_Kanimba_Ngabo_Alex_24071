package com.plantations.management.system.PMS.Repository;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Requests;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestsRepo extends JpaRepository<Requests,Integer>{
    List<Requests> findByOwnerId(Owners owners);
    Page<Requests> findAll(Pageable pageable);
    Page<Requests> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
}
