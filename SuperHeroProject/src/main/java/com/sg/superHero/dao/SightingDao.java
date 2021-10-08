/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.model.Sighting;
import java.util.List;

/**
 *
 * @author devon
 */
public interface SightingDao {
    Sighting addSighting(Sighting sighting);
    Sighting getSightingById(int id);
    
    List<Sighting> getSightingsForLocationById(int id);
    List<Sighting> getSightingsForDateById(String date);
    List<Sighting> getSightingsForSuperHumanById(int id);
    List<Sighting> getAllSightings();
    
    void updateSighting(Sighting sighting);
    void deleteSighting(int id);
}
