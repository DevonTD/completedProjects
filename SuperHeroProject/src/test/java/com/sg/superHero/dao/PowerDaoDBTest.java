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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class PowerDaoDBTest {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperHumanDao superHumanDao;


    public PowerDaoDBTest() {
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
    }

    /**
     * Test of getPowerById method, of class PowerDaoDB.
     */
    @Test
    public void testGetPowerAndAddPowerById() {
        Power power = new Power();
        power.setName("Fly");
        power.setDescription("You can Fly");
        power = powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getId());

        assertEquals(fromDao, power);
    }

    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power power = new Power();
        power.setName("Fly");
        power.setDescription("You can Fly");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power2.setName("Breath of fire");
        power2.setDescription("You can breath fire!");
        power2 = powerDao.addPower(power2);

        List<Power> powers = powerDao.getAllPowers();

        assertTrue(powers.contains(power));
        assertTrue(powers.contains(power2));
        assertEquals(powers.size(), 2);
    }

    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setName("Fly");
        power.setDescription("You can Fly");
        power = powerDao.addPower(power);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        SuperHuman superHuman = new SuperHuman();
        superHuman.setName("Tester");
        superHuman.setDescription("he tests things! What a hunk");
        superHuman.setHero(true);
        superHuman.setPowers(powers);
        superHuman = superHumanDao.addSuperHuman(superHuman);

        Power fromDao = powerDao.getPowerById(power.getId());

        assertEquals(fromDao, power);

        power.setName("fall");
        power.setDescription("You fall!");
        powerDao.updatePower(power);

        assertNotEquals(fromDao, power);

        fromDao = powerDao.getPowerById(power.getId());

        assertEquals(fromDao, power);
        assertEquals(superHuman.getPowers().get(0), power);
    }

    /**
     * Test of deletePower method, of class PowerDaoDB.
     */
    @Test
    public void testDeletePower() {
        Power power = new Power();
        power.setName("Fly");
        power.setDescription("You can Fly");
        power = powerDao.addPower(power);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        SuperHuman superHuman = new SuperHuman();
        superHuman.setName("Tester");
        superHuman.setDescription("he tests things! What a hunk");
        superHuman.setHero(true);
        superHuman.setPowers(powers);
        superHuman = superHumanDao.addSuperHuman(superHuman);
        
        Power fromDao = powerDao.getPowerById(power.getId());
        
        assertEquals(power, fromDao);
        
        powerDao.deletePower(power.getId());
        
        fromDao = powerDao.getPowerById(power.getId());
        assertNull(fromDao);
        List<Power> getSup = superHumanDao.getPowersForSuperHumanById(superHuman.getId());
        
        assertEquals(getSup.size(), 0);
    }

}
