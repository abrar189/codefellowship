package com.example.codefellowship.reposetory;

import com.example.codefellowship.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepo extends JpaRepository<ApplicationUser,Integer> {
     ApplicationUser findByUsername (String username);
}
