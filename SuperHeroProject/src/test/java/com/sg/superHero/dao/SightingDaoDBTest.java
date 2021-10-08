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
public class SightingDaoDBTest {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperHumanDao superHumanDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    public SightingDaoDBTest() {
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
     * Test of addSighting method, of class SightingDaoDB.
     */
    @Test
    public void testAddSightingGetSightingById() {
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

        Sighting fromDao = sightingDao.getSightingById(sight.getId());

        assertEquals(fromDao, sight);
    }

    /**
     * Test of getSightingsForLocationById method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForLocationById() {
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

        Location loc = new Location();
        loc.setName("Dandy lane");
        loc.setDescription("Dandy dandy hehe");
        loc.setStreetName("hello");
        loc.setStreetNumber("5412");
        loc.setState("MN");
        loc.setCity("Minneapolis");
        loc.setZip("55411");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc = locationDao.addLocation(loc);

        Sighting sight = new Sighting();
        sight.setName("Muppet Cave");
        sight.setDate(LocalDate.now());
        sight.setDescription("I seen him! He wasnt a spider but he used their lane! Is he a villian?!");
        sight.setLocation(location);
        sight.setSuperHuman(superHuman);
        sight = sightingDao.addSighting(sight);

        Sighting sighting = new Sighting();
        sighting.setName("Deadly Desire");
        sighting.setDate(LocalDate.now());
        sighting.setDescription("That man was up to somethin smh");
        sighting.setLocation(location);
        sighting.setSuperHuman(superHuman);
        sighting = sightingDao.addSighting(sighting);

        Sighting sights = new Sighting();
        sights.setName("Flavour Text");
        sights.setDate(LocalDate.now());
        sights.setDescription("he did it!!");
        sights.setLocation(loc);
        sights.setSuperHuman(superHuman);
        sights = sightingDao.addSighting(sights);

        List<Sighting> sightings = sightingDao.getSightingsForLocationById(location.getId());

        assertEquals(sightings.size(), 2);
        assertFalse(sightings.contains(sights));
        assertEquals(sightings.get(0).getLocation(), location);
        assertEquals(sightings.get(1).getLocation(), location);
    }

    /**
     * Test of getSightingsForDateById method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForDateById() {
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

        Sighting sighting = new Sighting();
        sighting.setName("Deadly Desire");
        sighting.setDate(LocalDate.now());
        sighting.setDescription("That man was up to somethin smh");
        sighting.setLocation(location);
        sighting.setSuperHuman(superHuman);
        sighting = sightingDao.addSighting(sighting);

        Sighting s = new Sighting();
        s.setName("Deadly Desire");
        String date = "2018-08-04";
        s.setDate(LocalDate.parse(date));
        s.setDescription("eefef");
        s.setLocation(location);
        s.setSuperHuman(superHuman);
        s = sightingDao.addSighting(s);

        List<Sighting> sightings = sightingDao.getSightingsForDateById(LocalDate.now().toString());

        assertEquals(sightings.size(), 2);
        assertFalse(sightings.contains(s));
        assertTrue(sightings.contains(sight));
        assertTrue(sightings.contains(sighting));

    }

    /**
     * Test of getSightingsForSuperHumanById method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForSuperHumanById() {
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

        Sighting sighting = new Sighting();
        sighting.setName("Deadly Desire");
        sighting.setDate(LocalDate.now());
        sighting.setDescription("That man was up to somethin smh");
        sighting.setLocation(location);
        sighting.setSuperHuman(superHuman);
        sighting = sightingDao.addSighting(sighting);

        Sighting s = new Sighting();
        s.setName("Deadly Desire");
        s.setDate(LocalDate.now());
        s.setDescription("eefef");
        s.setLocation(location);
        s.setSuperHuman(superH);
        s = sightingDao.addSighting(s);

        List<Sighting> sightings = sightingDao.getSightingsForSuperHumanById(superHuman.getId());

        assertEquals(sightings.size(), 2);
        assertEquals(sightings.get(0).getSuperHuman(), superHuman);
        assertEquals(sightings.get(1).getSuperHuman(), superHuman);
        assertFalse(sightings.contains(s));
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
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

        Sighting sighting = new Sighting();
        sighting.setName("Deadly Desire");
        sighting.setDate(LocalDate.now());
        sighting.setDescription("That man was up to somethin smh");
        sighting.setLocation(location);
        sighting.setSuperHuman(superHuman);
        sighting = sightingDao.addSighting(sighting);

        List<Sighting> sightings = sightingDao.getAllSightings();

        assertEquals(sightings.size(), 2);
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sight));

    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
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
        loc.setName("Dandy lane");
        loc.setDescription("Dandy dandy hehe");
        loc.setStreetName("hello");
        loc.setStreetNumber("5412");
        loc.setState("MN");
        loc.setCity("Minneapolis");
        loc.setZip("55411");
        loc.setLatitude(new BigDecimal("12.34"));
        loc.setLongitude(new BigDecimal("123.45"));
        loc = locationDao.addLocation(loc);

        Sighting sight = new Sighting();
        sight.setName("Deadly Desire");
        sight.setDate(LocalDate.now());
        sight.setDescription("I seen him! He wasnt a spider but he used their lane! Is he a villian?!");
        sight.setLocation(location);
        sight.setSuperHuman(superHuman);
        sight = sightingDao.addSighting(sight);

        Sighting fromDao = sightingDao.getSightingById(sight.getId());

        assertEquals(fromDao, sight);

        sight.setLocation(loc);
        sight.setSuperHuman(superH);
        sight.setDescription("Wrong people!!");
        sightingDao.updateSighting(sight);

        assertNotEquals(fromDao, sight);

        fromDao = sightingDao.getSightingById(sight.getId());

        assertEquals(fromDao, sight);

    }

    /**
     * Test of deleteSighting method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSighting() {
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

        Sighting fromDao = sightingDao.getSightingById(sight.getId());

        assertEquals(fromDao, sight);

        sightingDao.deleteSighting(sight.getId());
        Location fromDaoLocation = locationDao.getLocationById(location.getId());
        SuperHuman fromDaoSuperHuman = superHumanDao.getSuperHumanById(superHuman.getId());
        fromDao = sightingDao.getSightingById(sight.getId());

        assertNull(fromDao);
        assertNotNull(fromDaoLocation);
        assertNotNull(fromDaoSuperHuman);
    }

}
