/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.controller;

import com.sg.superHero.dao.SightingDao;
import com.sg.superHero.model.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author devon
 */
@Controller
public class HomeController {

    @Autowired
    SightingDao sightDao;

    @GetMapping("home")
    public String displaySighting(Model model) {
        List<Sighting> sightings = sightDao.getAllSightings();
        if (sightings.size() > 10) {
            sightings = sightings.subList(sightings.size() - 10, sightings.size());
            model.addAttribute("sightings", sightings);
        }else{
            model.addAttribute("sightings", sightings);
        }

        return "home";
    }

}
