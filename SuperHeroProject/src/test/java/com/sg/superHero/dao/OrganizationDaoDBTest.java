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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author devon
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperHumanDao superHumanDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    public OrganizationDaoDBTest() {
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

        List<Organization> orgs = organizationDao.getAllOrgs();
        for (Organization organization : orgs) {
            organizationDao.deleteOrg(organization.getId());
        }
    }

    /**
     * Test of addOrg method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddOrgGetOrgById() {
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
        superH.setHero(true);
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

        List<SuperHuman> superHumans = new ArrayList<>();
        superHumans.add(superHuman);
        superHumans.add(superH);

        Organization org = new Organization();
        org.setName("Jupiters Legacy");
        org.setDescription("A collection of planets");
        org.setPhone("123456790");
        org.setEmail("Devont.dixon@hehe.com");
        org.setSuperHumans(superHumans);
        org.setHero(true);
        org.setLocation(location);
        org = organizationDao.addOrg(org);

        Organization fromDao = organizationDao.getOrgById(org.getId());

        assertEquals(fromDao, org);
    }

    /**
     * Test of getSuperHumansForOrg method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetSuperHumansForOrg() {
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
        superH.setHero(true);
        superH.setPowers(powers);
        superH = superHumanDao.addSuperHuman(superH);

        SuperHuman sup = new SuperHuman();
        sup.setName("test");
        sup.setDescription("eiuhre");
        sup.setHero(false);
        sup.setPowers(powers);
        sup = superHumanDao.addSuperHuman(sup);

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

        List<SuperHuman> superHumans = new ArrayList<>();
        superHumans.add(superHuman);
        superHumans.add(superH);

        List<SuperHuman> supers = new ArrayList<>();
        supers.add(sup);

        Organization org = new Organization();
        org.setName("Jupiters Legacy");
        org.setDescription("A collection of planets");
        org.setPhone("123456790");
        org.setEmail("Devont.dixon@hehe.com");
        org.setSuperHumans(superHumans);
        org.setHero(true);
        org.setLocation(location);
        org = organizationDao.addOrg(org);

        Organization organization = new Organization();
        organization.setName("Bada bing bada BAD");
        organization.setDescription("They really bad");
        organization.setPhone("123456790");
        organization.setEmail("Devont.dixon@hehe.com");
        organization.setHero(false);
        organization.setSuperHumans(supers);
        organization.setLocation(location);
        organization = organizationDao.addOrg(organization);

        List<SuperHuman> superHumanz = organizationDao.getSuperHumansForOrg(org.getId());

        assertEquals(superHumanz.size(), 2);
        assertTrue(superHumanz.contains(superHuman));
        assertTrue(superHumanz.contains(superH));
        assertFalse(superHumanz.contains(sup));
    }

    /**
     * Test of getAllOrgsForSuperHuman method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrgsForSuperHuman() {
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
        superH.setHero(true);
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

        List<SuperHuman> superHumans = new ArrayList<>();
        superHumans.add(superHuman);
        superHumans.add(superH);

        List<SuperHuman> superHumanz = new ArrayList<>();
        superHumanz.add(superHuman);

        Organization org = new Organization();
        org.setName("Jupiters Legacy");
        org.setDescription("A collection of planets");
        org.setPhone("123456790");
        org.setEmail("Devont.dixon@hehe.com");
        org.setSuperHumans(superHumans);
        org.setHero(true);
        org.setLocation(location);
        org = organizationDao.addOrg(org);

        Organization organization = new Organization();
        organization.setName("Bada bing bada BAD");
        organization.setDescription("They really bad");
        organization.setPhone("123456790");
        organization.setEmail("Devont.dixon@hehe.com");
        organization.setHero(false);
        organization.setSuperHumans(superHumanz);
        organization.setLocation(location);
        organization = organizationDao.addOrg(organization);

        List<Organization> orgs = organizationDao.getAllOrgsForSuperHuman(superHuman.getId());

        assertEquals(orgs.size(), 2);
        assertTrue(orgs.get(0).getSuperHumans().contains(superHuman));
        assertTrue(orgs.get(1).getSuperHumans().contains(superHuman));
        assertFalse(orgs.get(1).getSuperHumans().contains(superH));
    }

    /**
     * Test of getAllOrgs method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrgs() {
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
        superH.setHero(true);
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

        List<SuperHuman> superHumans = new ArrayList<>();
        superHumans.add(superHuman);
        superHumans.add(superH);

        List<SuperHuman> superHumanz = new ArrayList<>();
        superHumanz.add(superHuman);

        Organization org = new Organization();
        org.setName("Jupiters Legacy");
        org.setDescription("A collection of planets");
        org.setPhone("123456790");
        org.setEmail("Devont.dixon@hehe.com");
        org.setSuperHumans(superHumans);
        org.setHero(true);
        org.setLocation(location);
        org = organizationDao.addOrg(org);

        Organization organization = new Organization();
        organization.setName("Bada bing bada BAD");
        organization.setDescription("They really bad");
        organization.setPhone("123456790");
        organization.setEmail("Devont.dixon@hehe.com");
        organization.setHero(false);
        organization.setLocation(location);
        organization.setSuperHumans(superHumanz);
        organization = organizationDao.addOrg(organization);

        List<Organization> orgs = organizationDao.getAllOrgs();

        assertEquals(orgs.size(), 2);
        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(organization));
    }

    /**
     * Test of updateOrg method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrg() {
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
        superH.setHero(true);
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

        List<SuperHuman> superHumans = new ArrayList<>();
        superHumans.add(superHuman);
        superHumans.add(superH);

        Organization org = new Organization();
        org.setName("Jupiters Legacy");
        org.setDescription("A collection of planets");
        org.setPhone("123456790");
        org.setEmail("Devont.dixon@hehe.com");
        org.setSuperHumans(superHumans);
        org.setHero(true);
        org.setLocation(location);
        org = organizationDao.addOrg(org);

        Organization fromDao = organizationDao.getOrgById(org.getId());
        SuperHuman fromSuper = superHumanDao.getSuperHumanById(superH.getId());

        assertEquals(fromSuper, superH);
        assertEquals(fromDao, org);

        superH.setDescription("He might just do it to em!");
        superHumanDao.updateSuperHuman(superH);

        assertNotEquals(fromSuper, superH);

        org.setName("The kool kids club LEL");
        org.setDescription("G3T H4CK3D N5RDZ");
        organizationDao.updateOrg(org);

        assertNotEquals(org, fromDao);

        fromDao = organizationDao.getOrgById(org.getId());

        assertEquals(fromDao, org);
    }

    /**
     * Test of deleteOrg method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrg() {
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

        List<SuperHuman> superHumans = new ArrayList<>();
        superHumans.add(superHuman);

        Organization org = new Organization();
        org.setName("Jupiters Legacy");
        org.setDescription("A collection of planets");
        org.setPhone("123456790");
        org.setEmail("Devont.dixon@hehe.com");
        org.setSuperHumans(superHumans);
        org.setHero(true);
        org.setLocation(location);
        org = organizationDao.addOrg(org);

        Organization fromDao = organizationDao.getOrgById(org.getId());

        assertEquals(fromDao, org);

        organizationDao.deleteOrg(org.getId());

        SuperHuman fromSuper = superHumanDao.getSuperHumanById(superHuman.getId());
        fromDao = organizationDao.getOrgById(org.getId());

        assertNull(fromDao);
        assertNotNull(fromSuper);
        assertEquals(fromSuper, superHuman);
    }

}
