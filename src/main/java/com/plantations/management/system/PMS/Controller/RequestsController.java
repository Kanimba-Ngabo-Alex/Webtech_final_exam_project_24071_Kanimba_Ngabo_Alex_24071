package com.plantations.management.system.PMS.Controller;

import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Plantations;
import com.plantations.management.system.PMS.Model.Requests;
import com.plantations.management.system.PMS.Model.Users;
import com.plantations.management.system.PMS.Service.OwnersService;
import com.plantations.management.system.PMS.Service.RequestsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping(value = "/requests")
public class RequestsController {
    @Autowired
    private RequestsService requestsService;
    @Autowired
    private OwnersService ownersService;
    //create
    @PostMapping(value = "/saveRequest")
    public String createRequest(@RequestParam("category") String category, @RequestParam("Description") String Description, @RequestParam("status") String status, HttpSession session) {
        Integer id=(Integer) session.getAttribute("loggedInOwnerId");
        Owners owners = ownersService.getOwnerByOwnerId(id);
        Requests requests = new Requests();
        requests.setOwnerId(owners);
        requests.setCategory(category);
        requests.setStatus(status);
        requests.setDescription(Description);
        requestsService.saveRequests(requests);
        return "redirect:/requests/OlistRequests";
    }
    //list
    @GetMapping(value = "/listRequests")
    public ResponseEntity<List<Requests>> listRequests() {
        List<Requests> requests = requestsService.listRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
    //update
    @PutMapping(value = "/updateRequest/{id}")
    public Requests updateRequest(@PathVariable Integer id, @RequestBody Requests requests) {
        return requestsService.updateRequests(requests, id);
    }
    //search
    @GetMapping("/newRequest")
    public String showNewRequestForm(Model model) {
        return "/newRequest";
    }
    @GetMapping("/OlistRequests")
    public String listByRequestsByOwnerId(Model model4, HttpSession session) {
        Integer loggedInOwnerId = (Integer) session.getAttribute("loggedInOwnerId");

        if (loggedInOwnerId != null) {
            List<Requests> requests = requestsService.getRequestsByOwnerId(loggedInOwnerId);
            model4.addAttribute("requests", requests);
            return "/OlistRequests";
        } else {
            return "redirect:/login";
        }
    }
}
