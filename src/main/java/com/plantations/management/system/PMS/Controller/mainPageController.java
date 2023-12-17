package com.plantations.management.system.PMS.Controller;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Plantations;
import com.plantations.management.system.PMS.Model.Requests;
import com.plantations.management.system.PMS.Service.CropsService;
import com.plantations.management.system.PMS.Service.OwnersService;
import com.plantations.management.system.PMS.Service.PlantationsService;
import com.plantations.management.system.PMS.Service.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class mainPageController {
    @Autowired
    private OwnersService ownersService;
    @Autowired
    private CropsService cropsService;
    @Autowired
    private RequestsService requestsService;
    @Autowired
    private PlantationsService plantationsService;
    @GetMapping("/PlantationManager")
    public String viewIndex(Model model) {
        return "index";
    }
    @GetMapping("/signUp")
    public String viewSignUp(Model model) {
        return "signUp";
    }
    @GetMapping("/login")
    public String viewLogin(Model model) {
        return "login";
    }
    @GetMapping("/ownerHome")
    public String viewOwnerHome(Model model) {
        return "ownerHome";
    }
    @GetMapping("/adminHome")
    public String viewAdminHome(Model model) {
        return "adminHome";
    }
    @GetMapping("/newOwner")
    public String viewNewOwner(Model model) {
        return "newOwner";
    }
    @GetMapping("/o")
    public String viewListAllOwners(Model model, @RequestParam(defaultValue = "") String searchTerm) {
        return listOwnersPaginated(1, "ownerId", "asc", searchTerm, model);
    }
    @GetMapping("/updateOwnersInfo/{id}")
    public String viewUpdateOwnerForm(@PathVariable Integer id, Model model) {
        model.addAttribute("ownerId", id);
        return "updateOwnersInfo";
    }

    @PostMapping(value="/saveUpdateOwner")
    public String saveUpdateOwner(HttpServletRequest request, @RequestParam("fullNames") String fullNames, @RequestParam("profilePicture") MultipartFile profilePicture) {
        try {
            // Retrieve ownerId from the request parameters
            Integer ownerId = Integer.parseInt(request.getParameter("ownerId"));

            Owners owner = ownersService.getOwnerByOwnerId(ownerId);
            if (owner != null) {
                byte[] profilePictureBytes = profilePicture.getBytes();
                owner.setFullNames(fullNames);
                owner.setProfilePicture(profilePictureBytes);
                ownersService.updateOwners(owner, owner.getOwnerId());
                return "redirect:/AlistOwners/1?sortField=ownerId&sortDir=asc";
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return "exception:" + e;
        }
        return "redirect:/AlistOwners/1?sortField=ownerId&sortDir=asc";
    }
    @GetMapping(value = "/AlistOwners/{pageNo}")
    public String listOwnersPaginated(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "ownerId") String sortField, @RequestParam(defaultValue = "asc") String sortDir, @RequestParam(defaultValue = "") String searchTerm, Model model) {

        int pageSize = 5;
        Page<Owners> page = ownersService.listOwnersPaginated(pageNo, pageSize, sortField, sortDir, searchTerm);
        List<Owners> owners = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("Owners", owners);
        model.addAttribute("searchTerm", searchTerm);

        return "AlistOwners";
    }



















    /*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    @GetMapping("/saveRequest")
    public String viewSaveRequest(Model model) {
        return "saveRequest";
    }
    @GetMapping("/newRequest")
    public String viewRequestForm(Model model) {
        return "newRequest";
    }
    @GetMapping("/r")
    public String viewListAllRequests(Model model, @RequestParam(defaultValue = "") String searchTerm) {
        return listRequestsPaginated(1, "requestId", "asc", searchTerm, model);
    }
    @GetMapping(value = "/AlistRequests/{pageNo}")
    public String listRequestsPaginated(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "requestId") String sortField, @RequestParam(defaultValue = "asc") String sortDir, @RequestParam(defaultValue = "") String searchTerm, Model model) {

        int pageSize = 5;
        Page<Requests> page = requestsService.listRequestsPaginated(pageNo, pageSize, sortField, sortDir, searchTerm);
        List<Requests> requests = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("Requests", requests);
        model.addAttribute("searchTerm", searchTerm);

        return "AlistRequests";
    }
    @GetMapping("/updateRequestsInfo/{id}")
    public String viewUpdateRequestForm(@PathVariable Integer id, Model model) {
        Requests request = requestsService.getRequestById(id);
        model.addAttribute("request", request);
        return "updateRequestsInfo";
    }
    @PostMapping(value="/saveUpdateRequest")
    public String saveUpdateRequest(@ModelAttribute("request") Requests request, @RequestParam("status")String status) {
        if (request != null) {
            request.setStatus(status);
            requestsService.updateRequests(request, request.getRequestId());
        }    return "redirect:/AlistRequests/1?sortField=requestId&sortDir=asc";
    }
    @GetMapping("/OlistRequests")
    public String viewOwnersRequests(Model model) {
        return "OlistRequests";
    }



















    /*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    @GetMapping("/c")
    public String viewListAllCrops(Model model, @RequestParam(defaultValue = "") String searchTerm) {
        return listCropsPaginated(1, "cropId", "asc", searchTerm, model);
    }
    @GetMapping("/updateCropsInfo/{id}")
    public String viewUpdateCropForm(@PathVariable Integer id, Model model) {
        Crops crop = cropsService.getCropsById(id);
        model.addAttribute("crop", crop);
        return "updateCropsInfo";
    }
    @PostMapping(value="/saveUpdateCrop")
    public String saveUpdateCrop(@ModelAttribute("crop") Crops crop, @RequestParam("cropName")String cropName) {
        if (crop != null) {
            crop.setCropName(cropName);
            cropsService.updateCrops(crop, crop.getCropId());
        }    return "redirect:/AlistCrops/1?sortField=cropId&sortDir=asc";
    }


    @GetMapping(value = "/AlistCrops/{pageNo}")
    public String listCropsPaginated(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "cropId") String sortField, @RequestParam(defaultValue = "asc") String sortDir, @RequestParam(defaultValue = "") String searchTerm, Model model) {

        int pageSize = 5;
        Page<Crops> page = cropsService.listCropsPaginated(pageNo, pageSize, sortField, sortDir, searchTerm);
        List<Crops> crops = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("Crops", crops);
        model.addAttribute("searchTerm", searchTerm);

        return "AlistCrops";
    }
    @GetMapping("/OlistCrops")
    public String viewOwnersListCrops(Model model) {
        return "OlistCrops";
    }
    @GetMapping("/saveCrop")
    public String viewCropForm(Model model) {
        return "saveCrop";
    }






















    @GetMapping("/p")
    public String viewListAllPlantations(Model model, @RequestParam(defaultValue = "") String searchTerm) {
        return listPlantationsPaginated(1, "plantationId", "asc", searchTerm, model);
    }
    @GetMapping("/updatePlantationsInfo/{id}")
    public String viewUpdatePlantationForm(@PathVariable Integer id, Model model) {
        Plantations plantation = plantationsService.getPlantationsById(id);
        model.addAttribute("plantation", plantation);
        return "updatePlantationsInfo";
    }
    @PostMapping(value="/deletePlantation/{id}")
    public String deletePlantation(@PathVariable Integer id) {
        plantationsService.deletePlantations(id);
        return "redirect:/AlistPlantations/1?sortField=plantationId&sortDir=asc";
    }
    @PostMapping(value="/saveUpdatePlantation")
    public String saveUpdatePlantation(@ModelAttribute("plantation") Plantations plantation, @RequestParam("location")String location, @RequestParam("ownerId")Integer ownerId, @RequestParam("cropId")Integer cropId) {
        Owners owners=ownersService.getOwnerByOwnerId(ownerId);
        Crops crops=cropsService.getCropsById(cropId);
        if (plantation != null) {
            plantation.setLocation(location);
            plantation.setOwnerId(owners);
            plantation.setCropId(crops);
            plantationsService.updatePlantations(plantation,plantation.getPlantationId());
        }    return "redirect:/AlistPlantations/1?sortField=plantationId&sortDir=asc";
    }

    @GetMapping("/downloadCSV")
    public void downloadCSV(HttpServletResponse response) {
        try {
            // Set response content type
            response.setContentType("text/csv");

            // Set header for the CSV attachment
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=plantation.csv";
            response.setHeader(headerKey, headerValue);

            // Get the table data
            List<Plantations> plantationsList = plantationsService.listPlantations();

            // Create a StringBuilder to store CSV content
            StringBuilder csvContent = new StringBuilder();

            // Write header
            csvContent.append("Plantation_Id, Owner, Crop Grown, Location\n");

            // Write data
            for (Plantations plantations : plantationsList) {
                csvContent.append(plantations.getPlantationId()).append(",").append(plantations.getOwnerId()).append(",").append(plantations.getCropId()).append(",").append(plantations.getLocation()).append("\n");
            }

            // Write CSV content to the response output stream
            response.getWriter().write(csvContent.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping(value = "/AlistPlantations/{pageNo}")
    public String listPlantationsPaginated(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "plantationId") String sortField, @RequestParam(defaultValue = "asc") String sortDir, @RequestParam(defaultValue = "") String searchTerm, Model model) {

        int pageSize = 5;
        Page<Plantations> page = plantationsService.listPlantationsPaginated(pageNo, pageSize, sortField, sortDir, searchTerm);
        List<Plantations> plantations = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("Plantations", plantations);
        model.addAttribute("searchTerm", searchTerm);

        return "AlistPlantations";
    }

    @GetMapping("/savePlantation")
    public String viewPlantationForm(Model model) {
        return "savePlantation";
    }
    @GetMapping("/OlistPlantations")
    public String viewOwnersListPlantations(Model model) {
        return "OlistPlantations";
    }

    @GetMapping("/AlistCrops")
    public String viewAdminListCrops(Model model) {
        return "AlistCrops";
    }
    /*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


















    @GetMapping("/Oprofile")
    public String viewOwnersInfo(Model model) {
        return "Oprofile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("logged_out", "Logged out successfully");
        return "redirect:/login";
    }
}
