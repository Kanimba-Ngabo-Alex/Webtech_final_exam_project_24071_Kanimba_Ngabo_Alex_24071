package com.plantations.management.system.PMS.Repository;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Plantations;
import com.plantations.management.system.PMS.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnersRepo extends JpaRepository<Owners,Integer> {
        boolean existsByUserId(Users userId);
        List<Owners> findByOwnerId(Integer ownerId);
        List<Owners> findByUserId(Users users);
        public Owners findOwnerByUserId(Users user);
        List<Owners> findByOwnerId(Owners owners);
        Page<Owners> findAll(Pageable pageable);
        Page<Owners> findByFullNamesContainingIgnoreCase(String fullNames, Pageable pageable);

}
