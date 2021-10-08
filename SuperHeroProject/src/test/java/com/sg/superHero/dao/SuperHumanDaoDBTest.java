/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.model.Location;
import com.sg.superHero.model.Organization;
import com.sg.superHero.model.Power;
import com.sg.superHero.model.Sighting;
import com.sg.superHero.model.SuperHuman;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author devon
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperHumanDaoDBTest {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperHumanDao superHumanDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    public SuperHumanDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePower(power.getId());
        }

        List<SuperHuman> superHumans = superHumanDao.getAllSuperHumans();
        for (SuperHuman superHuman : superHumans) {
            superHumanDao.deleteSuperHuman(superHuman.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocation(location.getId());
        }

        List<Organization> orgs = organizationDao.getAllOrgs();
        for (Organization organization : orgs) {
            organizationDao.deleteOrg(organization.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSighting(sighting.getId());
        }
    }

    /**
     * Test of addSuperHuman method, of class SuperHumanDaoDB.
     */
    @Test
    public void testAddSuperHumanGetSuperHumanById() {
        Power power = new Power();
        power.setName("fly");
        power.setDescription("flyyyying");
        power = powerDao.addPower(power);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        SuperHuman superHuman = new SuperHuman();
        superHuman.setName("Tests");
        superHuman.setDescription("He tests stuff");
        superHuman.setHero(true);
        superHuman.setPowers(powers);
        superHuman = superHumanDao.addSuperHuman(superHuman);

        SuperHuman fromDao = superHumanDao.getSuperHumanById(superHuman.getId());

        assertEquals(fromDao, superHuman);
    }

    /**
     * Test of getAllSuperHumans method, of class SuperHumanDaoDB.
     */
    @Test
    public void testGetAllSuperHumans() {
        Power power = new Power();
        power.setName("fly");
        power.setDescription("flyyyying");
        power = powerDao.addPower(power);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        SuperHuman superHuman = new SuperHuman();
        superHuman.setName("Tests");
        superHuman.setDescription("He tests stuff");
        superHuman.setHero(true);
        superHuman.setPowers(powers);
        superHuman = superHumanDao.addSuperHuman(superHuman);

        SuperHuman superH = new SuperHuman();
        superH.setName("Drake");
        superH.setDescription("Bababoey");
        superH.setHero(false);
        superH.setPowers(powers);
        superH = superHumanDao.addSuperHuman(superH);

        List<SuperHuman> supers = superHumanDao.getAllSuperHumans();

        assertTrue(supers.contains(superHuman));
        assertTrue(supers.contains(superH));
        assertEquals(supers.size(), 2);
    }

    /**
     * Test of updateSuperHuman method, of class SuperHumanDaoDB.
     */
    @Test
    public void testUpdateSuperHuman() {
        Power power = new Power();
        power.setName("fly");
        power.setDescription("flyyyying");
        power = powerDao.addPower(power);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        SuperHuman superHuman = new SuperHuman();
        superHuman.setName("Tests");
        superHuman.setDescription("He tests stuff");
        superHuman.setHero(true);
        superHuman.setPowers(powers);
        superHuman = superHumanDao.addSuperHuman(superHuman);

        SuperHuman fromDao = superHumanDao.getSuperHumanById(superHuman.getId());

        assertEquals(fromDao, superHuman);

        power.setDescription("You can fly at supersonice speeds");
        powerDao.updatePower(power);

        superHuman.setName("The big bad guy");
        superHuman.setHero(false);
        superHumanDao.updateSuperHuman(superHuman);

        assertNotEquals(fromDao, superHuman);

        fromDao = superHumanDao.getSuperHumanById(superHuman.getId());

        assertEquals(superHuman, fromDao);
    }

    /**
     * Test of deleteSuperHuman method, of class SuperHumanDaoDB.
     */
    @Test
    public void testDelteSuperHuman() {
        Power power = new Power();
        power.setName("fly");
        power.setDescription("flyyyying");
        power = powerDao.addPower(power);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        SuperHuman superHuman = new SuperHuman();
        superHuman.setName("Tests");
        superHuman.setDescription("He tests stuff");
        superHuman.setHero(true);
        superHuman.setPowers(powers);
        superHuman = superHumanDao.addSuperHuman(superHuman);

        SuperHuman fromDao = superHumanDao.getSuperHumanById(superHuman.getId());

        assertEquals(fromDao, superHuman);

        superHumanDao.deleteSuperHuman(superHuman.getId());

        fromDao = superHumanDao.getSuperHumanById(superHuman.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getPowersForSuperHumanById method, of class SuperHumanDaoDB.
     */
    @Test
    public void testGetPowersForSuperHumanById() {
        Power power = new Power();
        power.setName("fly");
        power.setDescription("flyyyying");
        power = powerDao.addPower(power);
        
        Power power2 = new Power();
        power2.setName("Kill word");
        power2.setDescription("You speak they die");
        power2 = powerDao.addPower(power2);
        
        Power power3 = new Power();
        power3.setName("egewjgf");
        power3.setDescription("Cthuluhu stuff");
        power3 = powerDao.addPower(power3);

        List<Power> powers = new ArrayList<>();
        powers.add(power);
        powers.add(power2);
        powers.add(power3);
        
        List<Power> powerrr = new ArrayList<>();
        powerrr.add(power);

        SuperHuman superHuman = new SuperHuman();
        superHuman.setName("Tests");
        superHuman.setDescription("He tests stuff");
        superHuman.setHero(true);
        superHuman.setPowers(powers);
        superHuman = superHumanDao.addSuperHuman(superHuman);
        
        SuperHuman superH = new SuperHuman();
        superH.setName("me");
        superH.setDescription("you");
        superH.setHero(false);
        superH.setPowers(powerrr);
        superH = superHumanDao.addSuperHuman(superH);
        
        List<Power> powersForSuperHuman = superHumanDao.getPowersForSuperHumanById(superHuman.getId());
        List<Power> powersForSuperH = superHumanDao.getPowersForSuperHumanById(superH.getId());
        
        assertTrue(powersForSuperHuman.contains(power));
        assertTrue(powersForSuperHuman.contains(power2));
        assertTrue(powersForSuperHuman.contains(power3));
        assertEquals(powersForSuperHuman.size(), 3);
        
        assertTrue(powersForSuperH.contains(power));
        assertEquals(powersForSuperH.size(), 1);
    }

}
