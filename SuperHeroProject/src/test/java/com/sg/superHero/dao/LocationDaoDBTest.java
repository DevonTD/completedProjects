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
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class LocationDaoDBTest {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperHumanDao superHumanDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    public LocationDaoDBTest() {
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

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSighting(sighting.getId());
        }
    }

    /**
     * Test of addLocation method, of class LocationDaoDB.
     */
    @Test
    public void testAddLocationGetLocationById() {
        Location location = new Location();
        location.setName("Spider Ave");
        location.setDescription("Spiders use this street to traverse!");
        location.setStreetName("hello");
        location.setStreetNumber("5412");
        location.setState("MN");
        location.setCity("Minneapolis");
        location.setZip("55411");
        location.setLatitude(new BigDecimal("12.34"));
        location.setLongitude(new BigDecimal("123.45"));
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());

        assertEquals(fromDao, location);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Spider Ave");
        location.setDescription("Spiders use this street to traverse!");
        location.setStreetName("hello");
        location.setStreetNumber("5412");
        location.setState("MN");
        location.setCity("Minneapolis");
        location.setZip("55411");
        location.setLatitude(new BigDecimal("12.34"));
        location.setLongitude(new BigDecimal("123.45"));
        location = locationDao.addLocation(location);

        Location loc = new Location();
        loc.setName("test");
        loc.setDescription("Location for testing");
        loc.setStreetName("hello");
        loc.setStreetNumber("5412");
        loc.setState("MN");
        loc.setCity("Minneapolis");
        loc.setZip("55411");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc = locationDao.addLocation(loc);

        List<Location> locations = locationDao.getAllLocations();

        assertTrue(locations.contains(location));
        assertTrue(locations.contains(loc));
        assertEquals(locations.size(), 2);
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpadteLocation() {
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

        Location location = new Location();
        location.setName("Spider Ave");
        location.setDescription("Spiders use this street to traverse!");
        location.setStreetName("hello");
        location.setStreetNumber("5412");
        location.setState("MN");
        location.setCity("Minneapolis");
        location.setZip("55411");
        location.setLatitude(new BigDecimal("12.34"));
        location.setLongitude(new BigDecimal("123.45"));
        location = locationDao.addLocation(location);

        Sighting sight = new Sighting();
        sight.setName("Deadly Desire");
        sight.setDate(LocalDate.now());
        sight.setDescription("I seen him! He wasnt a spider but he used their lane! Is he a villian?!");
        sight.setLocation(location);
        sight.setSuperHuman(superHuman);
        sight = sightingDao.addSighting(sight);

        Location fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
        assertEquals(sight.getLocation(), location);

        location.setDescription("Anyone can use this lane. Not Just spiders!");
        locationDao.updateLocation(location);

        assertNotEquals(fromDao, location);
        assertEquals(sight.getLocation(), location);

        fromDao = locationDao.getLocationById(location.getId());

        assertEquals(fromDao, location);
    }

    /**
     * Test of deleteLocation method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocation() {
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

        Location location = new Location();
        location.setName("Spider Ave");
        location.setDescription("Spiders use this street to traverse!");
        location.setStreetName("hello");
        location.setStreetNumber("5412");
        location.setState("MN");
        location.setCity("Minneapolis");
        location.setZip("55411");
        location.setLatitude(new BigDecimal("12.34"));
        location.setLongitude(new BigDecimal("123.45"));
        location = locationDao.addLocation(location);

        Sighting sight = new Sighting();
        sight.setName("Deadly Desire");
        sight.setDate(LocalDate.now());
        sight.setDescription("I seen him! He wasnt a spider but he used their lane! Is he a villian?!");
        sight.setLocation(location);
        sight.setSuperHuman(superHuman);
        sight = sightingDao.addSighting(sight);

        Location fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
        assertEquals(sight.getLocation(), location);

        locationDao.deleteLocation(location.getId());

        fromDao = locationDao.getLocationById(location.getId());
        Sighting fromDaoSight = sightingDao.getSightingById(sight.getId());

        assertNull(fromDao);
        assertNull(fromDaoSight);
    }

}
