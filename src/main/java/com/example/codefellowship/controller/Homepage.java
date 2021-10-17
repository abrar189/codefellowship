package com.example.codefellowship.controller;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.reposetory.ApplicationUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class Homepage {
    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal ApplicationUser user , Model model){
        if (user != null) {
            ApplicationUser findUser = applicationUserRepo.findByUsername(user.getUsername());
            model.addAttribute("user", findUser.getId());
        }
        return "home";
    }



    @GetMapping("/profile")
    public String profile(@RequestParam Long id , Model model){
        ApplicationUser user =  applicationUserRepo.findById(id).orElseThrow();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("dateOfBirth", user.getDateOfBirth());
        model.addAttribute("bio", user.getBio());
        return "profile";
    }

}
