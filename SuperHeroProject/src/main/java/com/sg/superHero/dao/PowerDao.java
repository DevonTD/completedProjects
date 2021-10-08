/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.model.Power;
import java.util.List;

/**
 *
 * @author devon
 */
public interface PowerDao {
    Power getPowerById(int id);
    List<Power> getAllPowers();
    Power addPower(Power power);
    
    void updatePower(Power power);
    void deletePower(int id);
}
