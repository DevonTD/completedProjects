/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.model.Power;
import com.sg.superHero.model.SuperHuman;
import java.util.List;

/**
 *
 * @author devon
 */
public interface SuperHumanDao {
    SuperHuman addSuperHuman(SuperHuman superHuman);
    SuperHuman getSuperHumanById(int id);
    List<SuperHuman> getAllSuperHumans();
    
    void updateSuperHuman(SuperHuman superHuman);
    void deleteSuperHuman(int id);
    
    List<Power> getPowersForSuperHumanById(int id);
}
