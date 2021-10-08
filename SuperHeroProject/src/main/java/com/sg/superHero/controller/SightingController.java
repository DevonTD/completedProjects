/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.controller;

import com.sg.superHero.dao.LocationDao;
import com.sg.superHero.dao.SightingDao;
import com.sg.superHero.dao.SuperHumanDao;
import com.sg.superHero.model.Location;
import com.sg.superHero.model.Sighting;
import com.sg.superHero.model.SuperHuman;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author devon
 */
@Controller
public class SightingController {

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @Autowired
    LocationDao locationDao;

    @Autowired
    SuperHumanDao superDao;

    @Autowired
    SightingDao sightDao;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        model.addAttribute("locations", locationDao.getAllLocations());
        model.addAttribute("superHumans", superDao.getAllSuperHumans());
        model.addAttribute("sightings", sightDao.getAllSightings());
        model.addAttribute("errors", violations);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        String superId = request.getParameter("superHumanId");

        Sighting sighting = new Sighting();
        sighting.setName(request.getParameter("name"));
        sighting.setDescription(request.getParameter("description"));
        sighting.setDate(LocalDate.parse(request.getParameter("date")));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setSuperHuman(superDao.getSuperHumanById(Integer.parseInt(superId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightDao.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("viewSighting")
    public String viewSighting(Integer id, Model model) {
        Sighting sight = sightDao.getSightingById(id);
        model.addAttribute("sighting", sight);

        return "sightingDetails";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightDao.deleteSighting(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sight = sightDao.getSightingById(id);
        List<Location> locations = locationDao.getAllLocations();
        List<SuperHuman> supers = superDao.getAllSuperHumans();
        model.addAttribute("locations", locations);
        model.addAttribute("superHumans", supers);
        model.addAttribute("sighting", sight);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String executeEditSighting(@Valid Sighting sight, BindingResult result,
            HttpServletRequest request, Model model) {

        String locationId = request.getParameter("locationId");
        String superId = request.getParameter("superHumanId");
        
        sight.setDate(LocalDate.parse(request.getParameter("date")));
        sight.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sight.setSuperHuman(superDao.getSuperHumanById(Integer.parseInt(superId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sight);

        if (violations.isEmpty()) {
            sightDao.updateSighting(sight);
        } else {
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("superHumans", superDao.getAllSuperHumans());
            return "editSighting";
        }

        return "redirect:/sightings";
    }
}
