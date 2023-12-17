package com.plantations.management.system.PMS.Controller;

import com.plantations.management.system.PMS.Model.Users;
import com.plantations.management.system.PMS.Service.UsersService;
import com.plantations.management.system.PMS.Model.Owners;
import com.plantations.management.system.PMS.Service.OwnersService;
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
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private OwnersService ownersService;

    //signup
    @PostMapping(value = "/signup")
    public String registerUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("role") String role) {
        Users newUse = new Users();
        newUse.setEmail(email);
        newUse.setPassword(password);
        newUse.setRole(role);
        usersService.saveUsers(newUse);
        return "/login";
    }

    //list
    @GetMapping(value = "/listUsers")
    public ResponseEntity<List<Users>> listUsers() {
        List<Users> users = usersService.listUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //update
    @PutMapping(value = "/updateUser/{id}")
    public Users updateUser(@PathVariable Integer id, @RequestBody Users users) {
        return usersService.updateUsers(users, id);
    }

    //delete
    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        if (id != null) {
            String message = usersService.deleteUsers(id);
            if (message != null) {
                return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User Not Deleted Successfully", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
    }

    //login
    @GetMapping(value = "/login")
    public String Login(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        Users users = usersService.UserByEmailANDPassword(email, password);
        if (users != null) {
            Integer loggedInUserId = users.getUserId();
            String role = users.getRole();
            if ("user".equals(role)) {
                List<Owners> owners = ownersService.getOwnersByUserId(users.getUserId());
                if (!owners.isEmpty()) {
                    Owners loggedInOwner = owners.get(0);
                    Integer loggedInOwnerId = loggedInOwner.getOwnerId();
                    session.setAttribute("loggedInOwnerId", loggedInOwnerId);
                    return "redirect:/ownerHome";
                } else {
                    session.setAttribute("loggedInUserId", users.getUserId());
                    return "redirect:/newOwner";
                }
            } else if ("admin".equals(role)) {
                return "redirect:/adminHome";
            } else {
                throw new RuntimeException("ERROR!");
            }
        } else {
            throw new RuntimeException("ERROR!");
        }
    }

}