package com.plantations.management.system.PMS.Service;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Plantations;
import com.plantations.management.system.PMS.Model.Users;
import com.plantations.management.system.PMS.Repository.OwnersRepo;
import com.plantations.management.system.PMS.Repository.PlantationsRepo;
import com.plantations.management.system.PMS.Repository.UsersRepo;
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
public class OwnersService {

    @Autowired
    private OwnersRepo ownersRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private PlantationsRepo plantationsRepo;
    public boolean isOwnerExists(Integer id) {

        return ownersRepo.existsById(id);
    }
    public String saveOwners(Owners owners){
        if (owners != null) {
            ownersRepo.save(owners);
            return "Owner saved successfully";

        } else {
            return null;
        }
    }
    public List<Owners> listOwners() {

        return ownersRepo.findAll();
    }
    public Owners updateOwners(Owners owners, Integer id){
        Optional<Owners> listOwners = ownersRepo.findById(id);
        if(listOwners.isPresent()){
            Owners present = listOwners.get();
            present.setFullNames(owners.getFullNames());
            return ownersRepo.save(present);
        }else {
            throw new RuntimeException("Id Not Found");
        }
    }
    public String deleteOwners(Integer id) {
        try {
            if (id != null) {
                if (isOwnerExists(id)) {
                    ownersRepo.deleteById(id);
                    return "Owner deleted successfully";
                } else {
                    return "Owner not found";
                }
            } else {
                return "Invalid input";
            }
        } catch (Exception e) {
            return "Owner not deleted";
        }
    }
    public List<Owners> getOwnersByUserId(Integer userId) {
        Users users = usersRepo.findById(userId).orElse(null);
        if (users != null) {
            return ownersRepo.findByUserId(users);
        } else {
            return null;
        }
    }
    public Owners getOwnerById(Integer id){
        Users users = usersRepo.findById(id).orElse(null);
        return ownersRepo.findOwnerByUserId(users);
    }
    public Owners getOwnerByOwnerId(Integer ownerId) {
        return ownersRepo.findById(ownerId).orElse(null);
    }
    public Page<Owners> listOwnersPaginated(int pageNo, int pageSize, String sortField, String sortDir, String searchTerm) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        if (StringUtils.hasText(searchTerm)) {
            Page<Owners> searchResult = ownersRepo.findByFullNamesContainingIgnoreCase(searchTerm, pageable);
            // Log the results and search term
            System.out.println("Search Term: " + searchTerm);
            System.out.println("Search Result: " + searchResult.getContent());
            return searchResult;
        } else {
            // Log the values when there's no search term
            System.out.println("No Search Term");
            Page<Owners> allOwners = ownersRepo.findAll(pageable);
            System.out.println("All Crops: " + allOwners.getContent());
            return allOwners;
        }
    }


}
