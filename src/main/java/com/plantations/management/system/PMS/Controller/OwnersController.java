package com.plantations.management.system.PMS.Controller;

import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Model.Plantations;
import com.plantations.management.system.PMS.Model.Users;
import com.plantations.management.system.PMS.Service.OwnersService;
import com.plantations.management.system.PMS.Service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
@Controller
@RequestMapping(value = "/owners")
public class OwnersController {
    @Autowired
    private OwnersService ownersService;
    @Autowired
    private UsersService usersService;
    //create
    @PostMapping(value = "/AInfo")
    public String registerOwnerInfo(@RequestParam("fullNames") String fullNames, @RequestParam("profilePicture") MultipartFile profilePicture, HttpSession session) {
        try {
            Integer userId = (Integer) session.getAttribute("loggedInUserId");
            if (userId != null) {
                Users newusers = usersService.UserByUserId(userId);
                if (newusers != null) {
                    byte[] profilePictureBytes = profilePicture.getBytes();
                    Owners newOwner = new Owners();
                    newOwner.setFullNames(fullNames);
                    newOwner.setUserId(newusers);
                    newOwner.setProfilePicture(profilePictureBytes);
                    ownersService.saveOwners(newOwner);
                    session.invalidate();
                    return "redirect:/login";
                } else {
                    return "redirect:/login";
                }
            }else{
                return "redirect:/login";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "exception:"+e;
        }
    }
    //list
    @GetMapping("/listOwners")
    public String listOwners(Model model) {
        List<Owners> owners = ownersService.listOwners();
        model.addAttribute("owners", owners);
        return "listOwners";
    }
    //update
    @PutMapping(value = "/updateOwner/{id}")
    public Owners updateOwner(@PathVariable Integer id, @RequestBody Owners owners) {
        return ownersService.updateOwners(owners, id);
    }
    //delete
    @DeleteMapping(value = "/deleteOwner/{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable Integer id) {
        if (id != null) {
            String message = ownersService.deleteOwners(id);
            if (message != null) {
                return new ResponseEntity<>("Owner Deleted Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Owner Not Deleted Successfully", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
    }
    //search
    @GetMapping("/Oprofile")
    public String listByOwnersByOwnerId(Model model9, HttpSession session) {
        Integer loggedInOwnerId = (Integer) session.getAttribute("loggedInOwnerId");

        if (loggedInOwnerId != null) {
            Owners owners = ownersService.getOwnerByOwnerId(loggedInOwnerId);

            if (owners != null) {
                model9.addAttribute("owners", Collections.singletonList(owners));
                model9.addAttribute("profilePictureBase64", convertProfilePictureToBase64(owners.getProfilePicture()));
                return "/Oprofile";
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
    }
    private String convertProfilePictureToBase64(byte[] profilePicture) {
        if (profilePicture != null && profilePicture.length > 0) {
            return Base64.encodeBase64String(profilePicture);
        }
        return null;
    }
}
