package com.plantations.management.system.PMS.Service;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Plantations;
import com.plantations.management.system.PMS.Repository.CropsRepo;
import com.plantations.management.system.PMS.Repository.OwnersRepo;
import com.plantations.management.system.PMS.Repository.PlantationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PlantationsService {
    @Autowired
    private OwnersRepo ownersRepo;
    @Autowired
    private CropsRepo cropsRepo;
    @Autowired
    private PlantationsRepo plantationsRepo;
    public boolean isPlantationExists(Integer id) {

        return plantationsRepo.existsById(id);
    }
    public String savePlantations(Plantations plantations){
        plantationsRepo.save(plantations);
        return "Plantation saved successfully";
    }
    public List<Plantations> listPlantations() {

        return plantationsRepo.findAll();
    }
    public Plantations updatePlantations(Plantations plantations, Integer id){
        Optional<Plantations> listPlantations = plantationsRepo.findById(id);
        if(listPlantations.isPresent()){
            Plantations present = listPlantations.get();
            present.setCropId(plantations.getCropId());
            present.setLocation(plantations.getLocation());
            present.setOwnerId(plantations.getOwnerId());
            return plantationsRepo.save(present);
        }else {
            throw new RuntimeException("Id Not Found");
        }
    }
    public String deletePlantations(Integer id) {
        try {
            if (id != null) {
                if (isPlantationExists(id)) {
                    plantationsRepo.deleteById(id);
                    return "Plantation deleted successfully";
                } else {
                    return "Plantation not found";
                }
            } else {
                return "Invalid input";
            }
        } catch (Exception e) {
            return "Plantation not deleted";
        }
    }
    public List<Plantations> getPlantationsByOwnerId(Integer ownerId) {
        Optional<Owners> ownerOptional = ownersRepo.findById(ownerId);

        if (ownerOptional.isPresent()) {
            Owners owner = ownerOptional.get();
            return plantationsRepo.findByOwnerId(owner);
        } else {
            return Collections.emptyList(); // Return an empty list when owner is not found
        }
    }
    public List<Plantations> getPlantationsByCropId(Integer cropId) {
        Crops crops = cropsRepo.findById(cropId).orElse(null);
        if (crops!= null) {
            return plantationsRepo.findByCropId(crops);
        } else {
            return null;
        }
    }
    public Plantations getPlantationsById(Integer id)
    {
        return plantationsRepo.findById(id).orElse(null);
    }
    public Page<Plantations> listPlantationsPaginated(int pageNo, int pageSize, String sortField, String sortDir, String searchTerm) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        if (StringUtils.hasText(searchTerm)) {
            Page<Plantations> searchResult = plantationsRepo.findByLocationContainingIgnoreCase(searchTerm, pageable);
            // Log the results and search term
            System.out.println("Search Term: " + searchTerm);
            System.out.println("Search Result: " + searchResult.getContent());
            return searchResult;
        } else {
            // Log the values when there's no search term
            System.out.println("No Search Term");
            Page<Plantations> allPlantations = plantationsRepo.findAll(pageable);
            System.out.println("All Crops: " + allPlantations.getContent());
            return allPlantations;
        }
    }
}
