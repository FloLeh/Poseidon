package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("app")
@RequiredArgsConstructor
public class LoginController {

    final private UserRepository userRepository;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("secure/article-details")
    public String getAllUserArticles(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("error")
    public String error(Model model) {
        String errorMessage= "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        return "403";
    }
}
