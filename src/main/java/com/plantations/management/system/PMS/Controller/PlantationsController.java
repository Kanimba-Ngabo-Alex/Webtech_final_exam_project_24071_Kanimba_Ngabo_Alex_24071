package com.plantations.management.system.PMS.Controller;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Plantations;
import com.plantations.management.system.PMS.Service.CropsService;
import com.plantations.management.system.PMS.Service.OwnersService;
import com.plantations.management.system.PMS.Service.PlantationsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping(value = "/plantations")
public class PlantationsController {
    @Autowired
    private PlantationsService plantationsService;
    @Autowired
    private OwnersService ownersService;
    @Autowired
    private CropsService cropsService;
    //create
    @PostMapping(value = "/savePlantation")
    public String createPlantation(@RequestParam("location")String location, @RequestParam("ownerId")Integer ownerId, @RequestParam("cropId")Integer cropId) {
        Owners owners=ownersService.getOwnerByOwnerId(ownerId);
        Crops crops=cropsService.getCropsById(cropId);
        Plantations plantations = new Plantations();
        plantations.setLocation(location);
        plantations.setOwnerId(owners);
        plantations.setCropId(crops);
        plantationsService.savePlantations(plantations);
        return "redirect:/p";
    }
    //list
    @GetMapping(value = "/AlistPlantations")
    public String AdminlistPlantations(Model model) {
        List<Plantations> plantations = plantationsService.listPlantations();
        model.addAttribute("plantations", plantations);
        return "listPlantations";
    }
    @GetMapping("/OlistCrops")
    public String OwnerlistPlantationsCrops(Model model2, HttpSession session) {
        Integer loggedInOwnerId = (Integer) session.getAttribute("loggedInOwnerId");

        if (loggedInOwnerId != null) {
            List<Plantations> plantationz = plantationsService.getPlantationsByOwnerId(loggedInOwnerId);
            model2.addAttribute("plantationz", plantationz);
            return "/OlistCrops";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/OlistPlantations")
    public String OwnerlistPlantations(Model model3, HttpSession session) {
        Integer loggedInOwnerId = (Integer) session.getAttribute("loggedInOwnerId");

        if (loggedInOwnerId != null) {
            List<Plantations> plantationx = plantationsService.getPlantationsByOwnerId(loggedInOwnerId);
            model3.addAttribute("plantationx", plantationx);
            return "/OlistPlantations";
        } else {
            return "redirect:/login";
        }
    }

    //update
   /* @PutMapping(value = "/updatePlantation/{id}")
    public Plantations updatePlantation(@PathVariable Integer id, @RequestBody Plantations plantations) {
        return plantationsService.updatePlantations(plantations, id);
    }
    //delete
    @DeleteMapping(value = "/deletePlantation/{id}")
    public ResponseEntity<String> deletePlantation(@PathVariable Integer id) {
        if (id != null) {
            String message = plantationsService.deletePlantations(id);
            if (message != null) {
                return new ResponseEntity<>("Plantation Deleted Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Plantation Not Deleted Successfully", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
    }
    //filterByOwner
    /*@GetMapping(value = "/PlantationsByOwnerId/{id}/")
    public ResponseEntity<List<Plantations>> listByOwnerId(@PathVariable Integer id) {
        List<Plantations> plantations = plantationsService.getPlantationsByOwnerId(id);
        return new ResponseEntity<>(plantations, HttpStatus.OK);
    }
    //filterByCrop
    @GetMapping(value = "/PlantationsByCropId/{id}/")
    public ResponseEntity<List<Plantations>> listByCropId(@PathVariable Integer id) {
        List<Plantations> plantations = plantationsService.getPlantationsByCropId(id);
        return new ResponseEntity<>(plantations, HttpStatus.OK);
    }*/
}
