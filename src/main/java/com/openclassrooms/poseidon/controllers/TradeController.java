package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.services.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Spring MVC controller for managing {@link Trade} entities.
 * Handles HTTP requests for listing, adding, updating, and deleting trades.
 */
@Controller
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    /**
     * Displays the list of all trades.
     *
     * @param model the model used to pass the list of trades to the view
     * @return the name of the view to render ("trade/list")
     */
    @GetMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * Displays the form for adding a new trade.
     *
     * @param trade an empty {@link Trade} object to bind the form data
     * @return the name of the view to render ("trade/add")
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    /**
     * Validates and saves a new trade if input is valid.
     *
     * @param trade the {@link Trade} object to be validated and saved
     * @param result binding result containing validation errors, if any
     * @return redirect to trade list on success or redisplays the form on validation error
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result) {
        if (result.hasErrors()) {
            return "trade/add";
        }

        tradeService.create(trade);
        return "redirect:/trade/list";
    }

    /**
     * Displays the update form for an existing trade.
     *
     * @param id the ID of the trade to update
     * @param model the model used to pass the existing trade to the view
     * @return the name of the view to render ("trade/update")
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("trade", tradeService.getById(id));
        return "trade/update";
    }

    /**
     * Updates an existing trade after validating input.
     *
     * @param id the ID of the trade to update
     * @param trade the {@link Trade} object with updated values
     * @param result binding result containing validation errors, if any
     * @param model the model used to pass trade data back to the view in case of error
     * @return redirect to trade list on success or redisplays the form on validation error
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            trade.setTradeId(id); // retain ID in case of repeated form errors
            model.addAttribute("trade", trade);
            return "trade/update";
        }

        tradeService.update(trade, id);
        return "redirect:/trade/list";
    }

    /**
     * Deletes a trade by its ID.
     *
     * @param id the ID of the trade to delete
     * @return redirect to the trade list after deletion
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
