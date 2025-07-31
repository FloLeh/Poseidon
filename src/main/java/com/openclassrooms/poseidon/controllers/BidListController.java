package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.services.BidListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Spring MVC controller for managing {@link BidList} entities.
 * Provides endpoints to list, add, update, and delete bids.
 */
@Controller
@RequiredArgsConstructor
public class BidListController {

    private final BidListService bidListService;

    /**
     * Displays the list of all bids.
     *
     * @param model the model used to pass attributes to the view
     * @return the name of the view to display ("bidList/list")
     */
    @GetMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * Displays the form to add a new bid.
     *
     * @param bidList an empty bid object to bind the form
     * @return the name of the view to display ("bidList/add")
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        return "bidList/add";
    }

    /**
     * Validates and saves a new bid if the input is valid.
     *
     * @param bidList the bid to validate and save
     * @param result  binding result containing validation errors, if any
     * @return redirects to the bid list on success, or redisplays the form on error
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result) {
        if (result.hasErrors()) {
            return "bidList/add";
        }

        bidListService.create(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Displays the form to update an existing bid.
     *
     * @param id    the ID of the bid to update
     * @param model the model used to pass the bid to the view
     * @return the name of the view to display ("bidList/update")
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidList", bidListService.getById(id));
        return "bidList/update";
    }

    /**
     * Updates an existing bid after validation.
     *
     * @param id       the ID of the bid to update
     * @param bidList  the bid object containing updated data
     * @param result   binding result containing validation errors, if any
     * @param model    the model used to pass data back to the view in case of errors
     * @return redirects to the bid list on success, or redisplays the form on error
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {

        if (result.hasErrors()) {
            bidList.setBidListId(id); // the ID is lost if validation fails twice
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }
        bidListService.update(bidList, id);
        return "redirect:/bidList/list";
    }

    /**
     * Deletes a bid by its ID.
     *
     * @param id the ID of the bid to delete
     * @return redirects to the bid list after deletion
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        bidListService.deleteById(id);
        return "redirect:/bidList/list";
    }
}
