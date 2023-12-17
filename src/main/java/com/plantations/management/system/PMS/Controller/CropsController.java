package com.plantations.management.system.PMS.Controller;

import com.plantations.management.system.PMS.Model.Crops;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Requests;
import com.plantations.management.system.PMS.Model.Users;
import com.plantations.management.system.PMS.Service.CropsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping(value = "/crops")
public class CropsController {
    @Autowired
    private CropsService cropsService;
    //create
    @PostMapping(value = "/saveCrop")
    public String createCrop(@RequestParam("cropName") String cropName) {
        Crops crops = new Crops();
        crops.setCropName(cropName);
        cropsService.saveCrops(crops);
        return "redirect:/c";
    }





}
