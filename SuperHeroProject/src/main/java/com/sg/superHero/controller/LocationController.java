/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.controller;

import com.sg.superHero.dao.LocationDao;
import com.sg.superHero.model.Location;
import com.sg.superHero.model.Power;
import java.math.BigDecimal;
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
public class LocationController {

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @Autowired
    LocationDao locationDao;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        model.addAttribute("locations", locationDao.getAllLocations());
        model.addAttribute("errors", violations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String stName = request.getParameter("streetName");
        String stNum = request.getParameter("streetNumber");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");
        String longi = request.getParameter("longitude");
        String lat = request.getParameter("latitude");

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setStreetName(stName);
        location.setStreetNumber(stNum);
        location.setState(state);
        location.setCity(city);
        location.setZip(zip);

        if (!longi.isEmpty()) {
            location.setLongitude(new BigDecimal(longi));
        }
        if (!lat.isEmpty()) {
            location.setLatitude(new BigDecimal(lat));
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if (violations.isEmpty()) {
            locationDao.addLocation(location);
            violations.clear();
        }

        return "redirect:/locations";
    }

    @GetMapping("locationDetails")
    public String locationDetail(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);

        return "locationDetails";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocation(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);

        return "editLocation";
    }

    @PostMapping("editLocation")
    public String executeEditLocation(@Valid Location location, BindingResult result) {
        if(result.hasErrors()){
            return "editLocation";
        }
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }
}
