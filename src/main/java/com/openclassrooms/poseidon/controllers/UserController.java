package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Spring MVC controller for managing {@link User} entities.
 * Provides endpoints for listing, adding, updating, and deleting users.
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Displays the list of all users.
     *
     * @param model the model used to pass user data to the view
     * @return the name of the view to render ("user/list")
     */
    @GetMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Displays the form for adding a new user.
     *
     * @param user an empty {@link User} object to bind the form data
     * @return the name of the view to render ("user/add")
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * Validates and creates a new user if the input is valid.
     *
     * @param user the {@link User} object to validate and save
     * @param result the binding result containing validation errors, if any
     * @return redirect to the user list if successful or redisplay form if validation fails
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.create(user);
        return "redirect:/user/list";
    }

    /**
     * Displays the update form for an existing user.
     * The password is cleared to avoid displaying it in the form.
     *
     * @param id the ID of the user to update
     * @param model the model used to pass the user to the view
     * @return the name of the view to render ("user/update")
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getById(id);
        user.setPassword(""); // Clear password before showing in the form
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Validates and updates an existing user.
     *
     * @param id the ID of the user to update
     * @param user the {@link User} object containing updated values
     * @param result the binding result containing validation errors, if any
     * @return redirect to the user list if successful or redisplay form if validation fails
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "user/update";
        }

        userService.update(user, id);
        return "redirect:/user/list";
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return redirect to the user list after deletion
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}
