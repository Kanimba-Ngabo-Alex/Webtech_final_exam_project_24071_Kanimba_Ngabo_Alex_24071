package com.plantations.management.system.PMS.Service;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Requests;
import com.plantations.management.system.PMS.Repository.OwnersRepo;
import com.plantations.management.system.PMS.Repository.RequestsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class RequestsService {
    @Autowired
    private RequestsRepo requestsRepo;
    @Autowired
    private OwnersRepo ownersRepo;
    public boolean isRequestExists(Integer id) {

        return requestsRepo.existsById(id);
    }
    public String saveRequests(Requests requests){
        if (requests != null) {
            requestsRepo.save(requests);
            return "Request sent successfully";

        } else {
            return null;
        }
    }
    public List<Requests> listRequests() {

        return requestsRepo.findAll();
    }

    public Requests updateRequests(Requests requests, Integer id){
        Optional<Requests> listRequests = requestsRepo.findById(id);
        if(listRequests.isPresent()){
            Requests present = listRequests.get();
            present.setStatus(requests.getStatus());
            return requestsRepo.save(present);
        }else {
            throw new RuntimeException("Id Not Found");
        }
    }
    public Requests getRequestById(Integer id)
    {
        return requestsRepo.findById(id).orElse(null);
    }

    public List<Requests> getRequestsByOwnerId(Integer ownerId) {
        Owners owner = ownersRepo.findById(ownerId).orElse(null);
        if (owner != null) {
            return requestsRepo.findByOwnerId(owner);
        } else {
            return null;
        }
    }
    public Page<Requests> listRequestsPaginated(int pageNo, int pageSize, String sortField, String sortDir, String searchTerm) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        if (StringUtils.hasText(searchTerm)) {
            Page<Requests> searchResult = requestsRepo.findByCategoryContainingIgnoreCase(searchTerm, pageable);
            // Log the results and search term
            System.out.println("Search Term: " + searchTerm);
            System.out.println("Search Result: " + searchResult.getContent());
            return searchResult;
        } else {
            // Log the values when there's no search term
            System.out.println("No Search Term");
            Page<Requests> allRequests = requestsRepo.findAll(pageable);
            System.out.println("All Crops: " + allRequests.getContent());
            return allRequests;
        }
    }

}
