package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.services.RuleNameService;
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
 * Spring MVC controller for managing {@link RuleName} entities.
 * Provides endpoints to list, create, update, and delete RuleName records.
 */
@Controller
@RequiredArgsConstructor
public class RuleNameController {

    private final RuleNameService ruleNameService;

    /**
     * Displays the list of all RuleName entities.
     *
     * @param model the model used to pass attributes to the view
     * @return the name of the view to display ("ruleName/list")
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Displays the form to add a new RuleName.
     *
     * @param ruleName an empty {@link RuleName} object used to bind the form
     * @return the name of the view to display ("ruleName/add")
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    /**
     * Validates and saves a new RuleName if input is valid.
     *
     * @param ruleName the RuleName object to validate and save
     * @param result binding result containing validation errors, if any
     * @return redirect to the ruleName list on success, or redisplays the form on error
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }

        ruleNameService.create(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Displays the form to update an existing RuleName.
     *
     * @param id the ID of the RuleName to update
     * @param model the model used to pass the existing RuleName to the view
     * @return the name of the view to display ("ruleName/update")
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ruleName", ruleNameService.getById(id));
        return "ruleName/update";
    }

    /**
     * Updates an existing RuleName after validation.
     *
     * @param id the ID of the RuleName to update
     * @param ruleName the RuleName object with updated data
     * @param result binding result containing validation errors, if any
     * @return redirect to the ruleName list on success, or redisplays the form on error
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }

        ruleNameService.update(ruleName, id);
        return "redirect:/ruleName/list";
    }

    /**
     * Deletes a RuleName by its ID.
     *
     * @param id the ID of the RuleName to delete
     * @return redirect to the ruleName list after deletion
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
