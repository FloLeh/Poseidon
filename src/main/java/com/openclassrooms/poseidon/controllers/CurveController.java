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


@Controller
@RequiredArgsConstructor
public class CurveController {
    final private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.create(curvePoint);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePoint", curvePointService.getById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePointService.update(curvePoint, id);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        curvePointService.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
