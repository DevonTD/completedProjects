/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.model.Location;
import java.util.List;

/**
 *
 * @author devon
 */
public interface LocationDao {
    Location addLocation(Location location);
    List<Location> getAllLocations();
    Location getLocationById(int id);
    void updateLocation(Location location);
    void deleteLocation(int id);
}
