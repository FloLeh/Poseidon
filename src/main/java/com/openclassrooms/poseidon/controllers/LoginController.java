package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for managing login functionality and restricted pages.
 */
@Controller
@RequestMapping("app")
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    /**
     * Displays the login form.
     *
     * @return the name of the view to display ("login")
     */
    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * Displays a list of all users in a secure area.
     *
     * @param model the model used to pass user data to the view
     * @return the name of the view to display ("user/list")
     */
    @GetMapping("secure/article-details")
    public String getAllUserArticles(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * Displays a custom error page when the user is unauthorized.
     *
     * @param model the model used to pass the error message
     * @return the name of the error view to display ("403")
     */
    @GetMapping("error")
    public String error(Model model) {
        String errorMessage = "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        return "403";
    }
}
