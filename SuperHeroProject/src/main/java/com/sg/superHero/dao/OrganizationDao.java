/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.model.Location;
import com.sg.superHero.model.Organization;
import com.sg.superHero.model.SuperHuman;
import java.util.List;

/**
 *
 * @author devon
 */
public interface OrganizationDao {
    Organization addOrg(Organization organization);
    Organization getOrgById(int id);
    List<Organization> getAllOrgs();
    void updateOrg(Organization organization);
    void deleteOrg(int id);
    
    List<SuperHuman> getSuperHumansForOrg(int id);
    List<Organization> getAllOrgsForSuperHuman(int id);
}