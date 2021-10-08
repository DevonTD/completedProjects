/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.controller;

import com.sg.superHero.dao.LocationDao;
import com.sg.superHero.dao.OrganizationDao;
import com.sg.superHero.dao.PowerDao;
import com.sg.superHero.dao.SuperHumanDao;
import com.sg.superHero.model.Organization;
import com.sg.superHero.model.Power;
import com.sg.superHero.model.SuperHuman;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author devon
 */
@Controller
public class SuperHumanController {

    Set<ConstraintViolation<SuperHuman>> violations = new HashSet<>();

    @Autowired
    SuperHumanDao superDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    PowerDao powerDao;

    @GetMapping("superHumans")
    public String displaySuperHumans(Model model) {
        model.addAttribute("powers", powerDao.getAllPowers());
        model.addAttribute("superHumans", superDao.getAllSuperHumans());
        model.addAttribute("errors", violations);
        return "superHumans";
    }

    @PostMapping("addSuperHuman")
    public String addSuper(HttpServletRequest request) {
        String[] powerIds = request.getParameterValues("powerId");

        String hero = request.getParameter("hero");
        Boolean bool = Boolean.parseBoolean(hero);

        SuperHuman superHuman = new SuperHuman();
        superHuman.setName(request.getParameter("name"));
        superHuman.setDescription(request.getParameter("description"));
        superHuman.setHero(bool);

        List<Power> powers = new ArrayList<>();
        if (powerIds != null) {
            for (String powerId : powerIds) {
                powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
            }
        }
        superHuman.setPowers(powers);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superHuman);

        if (violations.isEmpty()) {
            superDao.addSuperHuman(superHuman);
        }

        return "redirect:/superHumans";
    }

    @GetMapping("deleteSuperHuman")
    public String deleteSuperHuman(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superDao.deleteSuperHuman(id);
        return "redirect:/superHumans";
    }

    @GetMapping("viewSuperHuman")
    public String viewSuperHuman(Integer id, Model model) {
        SuperHuman superHuman = superDao.getSuperHumanById(id);
        model.addAttribute("organizations", organizationDao.getAllOrgsForSuperHuman(id));
        model.addAttribute("superHuman", superHuman);

        return "superHumanDetails";
    }

    @GetMapping("editSuperHuman")
    public String editSuperHuman(Integer id, Model model) {
        SuperHuman superHuman = superDao.getSuperHumanById(id);
        model.addAttribute("powers", powerDao.getAllPowers());
        model.addAttribute("superHuman", superHuman);

        return "editSuperHuman";
    }

    @PostMapping("editSuperHuman") // Adding shows relevant error messages, but breaks the list when performing edit
    public String executeEditSuperHuman(SuperHuman superHuman, BindingResult result,
            HttpServletRequest request, Model model) {
        String[] powerIds = request.getParameterValues("powerId");
        List<Power> powers = new ArrayList<>();
        if (powerIds != null) {
            for (String powerId : powerIds) {
                powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
            }
        } else {
            FieldError error = new FieldError("superHuman", "powers", "Please select at least one power");
            result.addError(error);
        }
        superHuman.setPowers(powers);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superHuman);

        if (violations.isEmpty()) {
            superDao.updateSuperHuman(superHuman);
        } else {
            model.addAttribute("powers", powerDao.getAllPowers());
            model.addAttribute("superHuman", superHuman);
            return "editSuperHuman";
        }

        return "redirect:/superHumans";
    }
}
