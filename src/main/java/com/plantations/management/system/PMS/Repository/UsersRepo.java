package com.plantations.management.system.PMS.Repository;

import com.plantations.management.system.PMS.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {
    List<Users> findByEmail(String email);
    public Users findByEmailAndPassword(String email, String password);
    public Users findUserByUserId(Integer id);
}
