package com.example.codefellowship.controller;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.reposetory.ApplicationUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String logInPage() {
        return "login";
    }


    @PostMapping("/signup")
    public RedirectView signUp(@ModelAttribute ApplicationUser applicationUser) {
        ApplicationUser newUser = new ApplicationUser(applicationUser.getUsername(),
                encoder.encode(applicationUser.getPassword()), applicationUser.getFirstName(),
                applicationUser.getLastName(), applicationUser.getDateOfBirth(), applicationUser.getBio());

        applicationUserRepo.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/login");
    }

}
