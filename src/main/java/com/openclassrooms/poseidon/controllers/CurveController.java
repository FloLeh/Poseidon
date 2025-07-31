package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.services.CurvePointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Spring MVC controller for managing {@link CurvePoint} entities.
 * Provides endpoints to list, add, update, and delete curve points.
 */
@Controller
@RequiredArgsConstructor
public class CurveController {

    private final CurvePointService curvePointService;

    /**
     * Displays the list of all curve points.
     *
     * @param model the model used to pass attributes to the view
     * @return the name of the view to display ("curvePoint/list")
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * Displays the form to add a new curve point.
     *
     * @param curvePoint an empty {@link CurvePoint} object used to bind the form
     * @return the name of the view to display ("curvePoint/add")
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Validates and saves a new curve point if the input is valid.
     *
     * @param curvePoint the curve point to validate and save
     * @param result     binding result containing validation errors, if any
     * @return redirects to the curve point list on success, or redisplays the form on error
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.create(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Displays the form to update an existing curve point.
     *
     * @param id    the ID of the curve point to update
     * @param model the model used to pass the curve point to the view
     * @return the name of the view to display ("curvePoint/update")
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePoint", curvePointService.getById(id));
        return "curvePoint/update";
    }

    /**
     * Updates an existing curve point after validation.
     *
     * @param id         the ID of the curve point to update
     * @param curvePoint the object containing updated data
     * @param result     binding result containing validation errors, if any
     * @return redirects to the curve point list on success, or redisplays the form on error
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePointService.update(curvePoint, id);
        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes a curve point by its ID.
     *
     * @param id the ID of the curve point to delete
     * @return redirects to the curve point list after deletion
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        curvePointService.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
