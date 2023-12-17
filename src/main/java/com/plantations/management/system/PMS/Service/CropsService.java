package com.plantations.management.system.PMS.Service;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Repository.CropsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CropsService {
    @Autowired
    private CropsRepo cropsRepo;
    public boolean isCropExists(Integer id) {

        return cropsRepo.existsById(id);
    }
    public String saveCrops(Crops crops){
        cropsRepo.save(crops);
        return "Crop created successfully";

    }
    public List<Crops> listCrops() {

        return cropsRepo.findAll();
    }

    public Crops updateCrops(Crops crops, Integer id){
        Optional<Crops> listCrops = cropsRepo.findById(id);
        if(listCrops.isPresent()){
            Crops present = listCrops.get();
            present.setCropName(crops.getCropName());
            return cropsRepo.save(present);
        }else {
            throw new RuntimeException("Id Not Found");
        }
    }

    public String deleteCrops(Integer id) {
        try {
            if (id != null) {
                if (isCropExists(id)) {
                    cropsRepo.deleteById(id);
                    return "User deleted successfully";
                } else {
                    return "User not found";
                }
            } else {
                return "Invalid input";
            }
        } catch (Exception e) {
            return "User not deleted";
        }
    }
    public Crops getCropsById(Integer id)
    {
        return cropsRepo.findById(id).orElse(null);
    }

    public Page<Crops> listCropsPaginated(int pageNo, int pageSize, String sortField, String sortDir, String searchTerm) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        if (StringUtils.hasText(searchTerm)) {
            Page<Crops> searchResult = cropsRepo.findByCropNameContainingIgnoreCase(searchTerm, pageable);
            // Log the results and search term
            System.out.println("Search Term: " + searchTerm);
            System.out.println("Search Result: " + searchResult.getContent());
            return searchResult;
        } else {
            // Log the values when there's no search term
            System.out.println("No Search Term");
            Page<Crops> allCrops = cropsRepo.findAll(pageable);
            System.out.println("All Crops: " + allCrops.getContent());
            return allCrops;
        }
    }

}
