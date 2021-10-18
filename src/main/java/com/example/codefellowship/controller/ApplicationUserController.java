package com.example.codefellowship.controller;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.Post;
import com.example.codefellowship.reposetory.ApplicationUserRepo;
import com.example.codefellowship.reposetory.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/signup")
    public String signUpPage(@AuthenticationPrincipal ApplicationUser user, Model model) {
        if (user != null)
            model.addAttribute("username", applicationUserRepo.findByUsername(user.getUsername()).getUsername());
        return "signup";
    }

    @GetMapping("/login")
    public String logInPage(@AuthenticationPrincipal ApplicationUser user, Model model) {
        if (user != null)
            model.addAttribute("user", applicationUserRepo.findByUsername(user.getUsername()).getUsername());
        return "login";
    }

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal ApplicationUser user, Model model) {
        if (user != null) {
            ApplicationUser findUser = applicationUserRepo.findByUsername(user.getUsername());
            model.addAttribute("user", findUser.getUsername());
        }
        return "home";
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



    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal ApplicationUser user, Model model) {
        if (user != null) {
            Optional<ApplicationUser> currentUser = Optional.ofNullable(applicationUserRepo.findByUsername(user.getUsername()));
            model.addAttribute("userId", currentUser.get().getId());
            model.addAttribute("username", currentUser.get().getUsername());
            model.addAttribute("firstName", currentUser.get().getFirstName());
            model.addAttribute("lastName", currentUser.get().getLastName());
            model.addAttribute("dateOfBirth", currentUser.get().getDateOfBirth());
            model.addAttribute("bio", currentUser.get().getBio());

            List<Post> postList = postRepository.findAllByUser(currentUser);
            model.addAttribute("postList", postList);
        }
        return "profile";
    }




    @PostMapping("/addpost")
    public RedirectView addPost(@AuthenticationPrincipal ApplicationUser user, @RequestParam String body) {
        ApplicationUser newUser = applicationUserRepo.findByUsername(user.getUsername());
        Post addNewPost = new Post(body, newUser);
        postRepository.save(addNewPost);
        return new RedirectView("/profile");
    }

}
