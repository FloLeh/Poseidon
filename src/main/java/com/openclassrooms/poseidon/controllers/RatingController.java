package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.services.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Spring MVC controller for managing {@link Rating} entities.
 * Provides endpoints to list, create, update, and delete ratings.
 */
@Controller
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    /**
     * Displays the list of all ratings.
     *
     * @param model the model used to pass attributes to the view
     * @return the name of the view to display ("rating/list")
     */
    @GetMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Displays the form to add a new rating.
     *
     * @param rating an empty {@link Rating} object used to bind the form
     * @return the name of the view to display ("rating/add")
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Validates and saves a new rating if the input is valid.
     *
     * @param rating the rating object to validate and save
     * @param result binding result containing validation errors, if any
     * @param model  the model used to pass attributes to the view (optional here)
     * @return redirect to the rating list on success, or redisplays the form on error
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.create(rating);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Displays the form to update an existing rating.
     *
     * @param id    the ID of the rating to update
     * @param model the model used to pass the rating to the view
     * @return the name of the view to display ("rating/update")
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rating", ratingService.getById(id));
        return "rating/update";
    }

    /**
     * Updates an existing rating after validation.
     *
     * @param id      the ID of the rating to update
     * @param rating  the rating object containing updated data
     * @param result  binding result containing validation errors, if any
     * @param model   the model used to pass attributes to the view
     * @return redirect to the rating list on success, or redisplays the form on error
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }

        ratingService.update(rating, id);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Deletes a rating by its ID.
     *
     * @param id    the ID of the rating to delete
     * @param model the model used to pass updated data to the view
     * @return redirect to the rating list after deletion
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteById(id);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
}
