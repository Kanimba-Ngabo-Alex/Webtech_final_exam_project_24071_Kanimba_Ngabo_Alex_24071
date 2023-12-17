package com.plantations.management.system.PMS.Service;

import com.plantations.management.system.PMS.Model.Users;
import com.plantations.management.system.PMS.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;
    public List<Users> isUserExists(String email) {

        return usersRepo.findByEmail(email);
    }
    public boolean isUserIdExists(Integer id) {

        return usersRepo.existsById(id);
    }
    public String saveUsers(Users users){
        if (users != null) {
            List<Users> existingUsers = isUserExists(users.getEmail());

            if (!existingUsers.isEmpty()) {
                return "User already exists";
            }else {
                usersRepo.save(users);
                return "User created successfully";
            }
        } else {
            return null;
        }
    }
    public List<Users> listUsers() {

        return usersRepo.findAll();
    }

    public Users updateUsers(Users users, Integer id){
        Optional<Users> listUsers = usersRepo.findById(id);
        if(listUsers.isPresent()){
            Users present = listUsers.get();
            present.setPassword(users.getPassword());
            present.setRole(users.getRole());
            return usersRepo.save(present);
        }else {
            throw new RuntimeException("Id Not Found");
        }
    }

    public String deleteUsers(Integer id) {
        try {
            if (id != null) {
                if (isUserIdExists(id)) {
                    usersRepo.deleteById(id);
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
    public Users UserByEmailANDPassword(String email, String password){

        return usersRepo.findByEmailAndPassword(email, password );
    }

    public Users UserByUserId(Integer id){
        return usersRepo.findUserByUserId(id);
    }
}
