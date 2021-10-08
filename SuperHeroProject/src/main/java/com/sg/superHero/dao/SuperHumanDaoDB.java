/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.dao.OrganizationDaoDB.OrganizationMapper;
import com.sg.superHero.dao.PowerDaoDB.PowerMapper;
import com.sg.superHero.dao.SightingDaoDB.SightingMapper;
import com.sg.superHero.model.Location;
import com.sg.superHero.model.Organization;
import com.sg.superHero.model.Power;
import com.sg.superHero.model.Sighting;
import com.sg.superHero.model.SuperHuman;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author devon
 */
@Repository
public class SuperHumanDaoDB implements SuperHumanDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public SuperHuman addSuperHuman(SuperHuman superHuman) {
        final String INSERT_SUPER_HUMAN = "INSERT INTO superHuman (name, description, hero) VALUES (?,?,?)";
        jdbc.update(INSERT_SUPER_HUMAN,
                superHuman.getName(),
                superHuman.getDescription(),
                superHuman.isHero());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superHuman.setId(newId);
        insertSuperHumanPower(superHuman);
        return superHuman;
    }

    private void insertSuperHumanPower(SuperHuman superHuman) {
        final String INSERT_INTO_SUPER_HUMAN_POWER = "INSERT INTO super_human_power (superHumanId, powerId) VALUES (?,?)";
        for (Power power : superHuman.getPowers()) {
            jdbc.update(INSERT_INTO_SUPER_HUMAN_POWER, superHuman.getId(), power.getId());
        }
    }

    @Override
    public SuperHuman getSuperHumanById(int id) {
        try {
            final String GET_SUPER_HUMAN_BY_ID = "SELECT * FROM superHuman WHERE id = ?";
            SuperHuman superHuman = jdbc.queryForObject(GET_SUPER_HUMAN_BY_ID, new SuperHumanMapper(), id);
            superHuman.setPowers(getPowersForSuperHuman(id));
            return superHuman;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperHuman> getAllSuperHumans() {
        final String GET_ALL_SUPER_HUMANS = "SELECT * FROM superHuman";
        List<SuperHuman> superHumans = jdbc.query(GET_ALL_SUPER_HUMANS, new SuperHumanMapper());
        givePowers(superHumans);
        return superHumans;
    }

    private List<Power> getPowersForSuperHuman(int id) {
        final String GET_POWERS_FOR_SUPER_HUMAN = "SELECT p.* FROM power p JOIN super_human_power shp ON shp.powerId = p.id WHERE shp.superHumanId = ?";
        return jdbc.query(GET_POWERS_FOR_SUPER_HUMAN, new PowerMapper(), id);
    }

    private void givePowers(List<SuperHuman> superHumans) {
        for (SuperHuman superHuman : superHumans) {
            superHuman.setPowers(getPowersForSuperHuman(superHuman.getId()));
        }
    }

    @Override
    @Transactional
    public void updateSuperHuman(SuperHuman superHuman) {
        List<Sighting> sightings = getSightingForSuperHuman(superHuman.getId());
        List<Organization> orgs = getOrganizationsForSuperHuman(superHuman.getId());
        final String UDPATE_SUPER_HUMAN = "UPDATE superHuman SET name = ?, description = ?, hero = ? WHERE id = ?";
        jdbc.update(UDPATE_SUPER_HUMAN,
                superHuman.getName(),
                superHuman.getDescription(),
                superHuman.isHero(),
                superHuman.getId());

        final String DELETE_SUPER_HUMAN_ORGANIZATION_AFFILIATION = "DELETE FROM super_human_organization_affiliation WHERE superHumanId = ?";
        jdbc.update(DELETE_SUPER_HUMAN_ORGANIZATION_AFFILIATION, superHuman.getId());
        insertSuperHumanAffiliationOrgs(orgs, superHuman);

        final String DELETE_SUPER_HUMAN_SIGHTING = "DELETE FROM sighting WHERE superHumanId = ?";
        jdbc.update(DELETE_SUPER_HUMAN_SIGHTING, superHuman.getId());
        insertSuperHeroSightings(sightings, superHuman);

        final String DELETE_SUPER_HUMAN_POWER = "DELETE FROM super_human_power WHERE superHumanId = ?";
        jdbc.update(DELETE_SUPER_HUMAN_POWER, superHuman.getId());
        insertSuperHumanPower(superHuman);
    }

    private void insertSuperHumanAffiliationOrgs(List<Organization> orgs, SuperHuman superHuman) {
        final String INSERT_INTO_ORG_AFFILIATION = "INSERT INTO super_human_organization_affiliation "
                + "(superHumanId, organizationId) VALUES(?,?)";
        for (int i = 0; i < orgs.size(); i++) {
            jdbc.update(INSERT_INTO_ORG_AFFILIATION,
                    superHuman.getId(),
                    orgs.get(i).getId());
        }
    }

    private void insertSuperHeroSightings(List<Sighting> sightings, SuperHuman superHuman) {
        final String INSERT_INTO_SIGHTINS = "INSERT INTO sighting (name, date, description, superHumanId, locationId) VALUES (?,?,?,?,?)";
        for (int i = 0; i < sightings.size(); i++) {
            jdbc.update(INSERT_INTO_SIGHTINS,
                    sightings.get(i).getName(),
                    sightings.get(i).getDate(),
                    sightings.get(i).getDescription(),
                    superHuman.getId(),
                    sightings.get(i).getLocation().getId());
        }
    }

    private List<Sighting> getSightingForSuperHuman(int id) {
        final String GET_SIGHTINGS = "SELECT s.* FROM sighting s JOIN superHuman sh ON sh.id = s.superHumanId WHERE s.superHumanid = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTINGS, new SightingMapper(), id);
        associateLocationsWithSightings(sightings);
        return sightings;
    }

    private List<Organization> getOrganizationsForSuperHuman(int id) {
        final String GET_ORGS = "SELECT org.* FROM organization org JOIN super_human_organization_affiliation shoa ON org.id = shoa.organizationId WHERE shoa.superHumanId = ?";
        List<Organization> orgs = jdbc.query(GET_ORGS, new OrganizationMapper(), id);
        return orgs;
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION = "SELECT l.* FROM location l JOIN sighting s ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION, new LocationDaoDB.LocationMapper(), id);
    }

    private void associateLocationsWithSightings(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
        }
    }

    @Override
    @Transactional
    public void deleteSuperHuman(int id) {
        final String DELETE_SUPER_HUMAN_POWER = "DELETE FROM super_human_power WHERE superHumanId = ?";
        jdbc.update(DELETE_SUPER_HUMAN_POWER, id);

        final String DELETE_SUPER_HUMAN_ORG_AFFILIATION = "DELETE FROM super_human_organization_affiliation WHERE superHumanId = ?";
        jdbc.update(DELETE_SUPER_HUMAN_ORG_AFFILIATION, id);

        final String DELETE_FROM_SIGHTING = "DELETE FROM sighting WHERE superHumanId = ?";
        jdbc.update(DELETE_FROM_SIGHTING, id);

        final String DELTE_SUPER_HUMAN = "DELETE FROM superHuman WHERE id = ?";
        jdbc.update(DELTE_SUPER_HUMAN, id);
    }

    @Override
    public List<Power> getPowersForSuperHumanById(int id) {
        final String GET_POWERS_FOR_SUPER_HUMAN_BY_ID = "SELECT p.* FROM power p JOIN super_human_power shp ON shp.powerId = p.id WHERE shp.superHumanId = ?";
        List<Power> powers = jdbc.query(GET_POWERS_FOR_SUPER_HUMAN_BY_ID, new PowerMapper(), id);
        return powers;
    }

    public static final class SuperHumanMapper implements RowMapper<SuperHuman> {

        @Override
        public SuperHuman mapRow(ResultSet rs, int index) throws SQLException {
            SuperHuman superHuman = new SuperHuman();
            superHuman.setId(rs.getInt("id"));
            superHuman.setName(rs.getString("name"));
            superHuman.setDescription(rs.getString("description"));
            superHuman.setHero(rs.getBoolean("hero"));
            return superHuman;
        }
    }

}
