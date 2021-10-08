/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.controller;

import com.sg.superHero.dao.PowerDao;
import com.sg.superHero.model.Power;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author devon
 */
@Controller
public class PowerController {

    Set<ConstraintViolation<Power>> violations = new HashSet<>();

    @Autowired
    PowerDao powerDao;

    @GetMapping("powers")
    public String displayPowers(Model model) {
        model.addAttribute("powers", powerDao.getAllPowers());
        model.addAttribute("errors", violations);
        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Power power = new Power();
        power.setName(name);
        power.setDescription(description);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(power);

        if (violations.isEmpty()) {
            powerDao.addPower(power);
        }

        return "redirect:/powers";
    }

    @GetMapping("getPower")
    public String getPower(Integer id, Model model) {
        Power power = powerDao.getPowerById(id);
        model.addAttribute("power", power);
        return "redirect:/powers";
    }

    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        powerDao.deletePower(id);

        return "redirect:/powers";
    }

    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);

        model.addAttribute("power", power);
        return "editPower";
    }

    @PostMapping("editPower")
    public String executeEditPower(@Valid Power power, BindingResult result) {
        if(result.hasErrors()){
            return "editPower";
        }
        powerDao.updatePower(power);
        return "redirect:/powers";
    }
}
