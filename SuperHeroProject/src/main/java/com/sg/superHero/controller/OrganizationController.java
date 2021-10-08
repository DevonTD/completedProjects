/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.controller;

import com.sg.superHero.dao.LocationDao;
import com.sg.superHero.dao.OrganizationDao;
import com.sg.superHero.dao.SuperHumanDao;
import com.sg.superHero.model.Location;
import com.sg.superHero.model.Organization;
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
public class OrganizationController {

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperHumanDao superDao;

    @Autowired
    LocationDao locationDao;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        model.addAttribute("superHumans", superDao.getAllSuperHumans());
        model.addAttribute("organizations", organizationDao.getAllOrgs());
        model.addAttribute("locations", locationDao.getAllLocations());
        model.addAttribute("errors", violations);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superHumanId");
        String locationId = request.getParameter("locationId");

        String hero = request.getParameter("hero");
        Boolean bool = Boolean.parseBoolean(hero);

        Organization org = new Organization();
        org.setName(request.getParameter("name"));
        org.setDescription(request.getParameter("description"));
        org.setPhone(request.getParameter("phone"));
        org.setEmail(request.getParameter("email"));
        org.setHero(bool);
        org.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        List<SuperHuman> supers = new ArrayList<>();
        if (superIds != null) {
            for (String superId : superIds) {
                supers.add(superDao.getSuperHumanById(Integer.parseInt(superId)));
            }
        }
        org.setSuperHumans(supers);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if (violations.isEmpty()) {
            organizationDao.addOrg(org);
        }

        return "redirect:/organizations";
    }

    @GetMapping("viewOrganization")
    public String viewOrganization(Integer id, Model model) {
        Organization org = organizationDao.getOrgById(id);
        model.addAttribute("organization", org);

        return "organizationDetails";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        organizationDao.deleteOrg(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization org = organizationDao.getOrgById(id);
        model.addAttribute("organization", org);
        model.addAttribute("superHumans", superDao.getAllSuperHumans());
        model.addAttribute("locations", locationDao.getAllLocations());

        return "editOrganization";
    }

    @PostMapping("editOrganization") // Adding shows relevant error messages, but breaks the list when performing edit
    public String executeEditOrganization( Organization org,
            BindingResult result, HttpServletRequest request, Model model) { 
        String locationId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superHumanId");

        org.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        List<SuperHuman> supers = new ArrayList<>();
        if (superIds != null) {
            for (String superId : superIds) {
                supers.add(superDao.getSuperHumanById(Integer.parseInt(superId)));
            }
        } else {
            FieldError error = new FieldError("organization", "superHumans", "Please select at least one Super Human");
            result.addError(error);
        }
        org.setSuperHumans(supers);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if (violations.isEmpty()) {
            organizationDao.updateOrg(org);
        } else {
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("superHumans", superDao.getAllSuperHumans());
            model.addAttribute("organization", org);
            return "editOrganization";
        }

        return "redirect:/organizations";
    }
}
