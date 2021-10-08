/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.dao.LocationDaoDB.LocationMapper;
import com.sg.superHero.dao.SuperHumanDaoDB.SuperHumanMapper;
import com.sg.superHero.model.Location;
import com.sg.superHero.model.Power;
import com.sg.superHero.model.Sighting;
import com.sg.superHero.model.SuperHuman;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author devon
 */
@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting (name, date, description, superHumanId, locationId) VALUES (?,?,?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getName(),
                sighting.getDate(),
                sighting.getDescription(),
                sighting.getSuperHuman().getId(),
                sighting.getLocation().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setSuperHuman(getSuperHumanForSighting(id));
            sighting.getSuperHuman().setPowers(getPowersForSuperHuman(sighting.getSuperHuman().getId()));
            sighting.setLocation(getLocationForSighting(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION = "SELECT l.* FROM location l JOIN sighting s ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION, new LocationMapper(), id);
    }

    private SuperHuman getSuperHumanForSighting(int id) {
        final String SELECT_SUPER_HUMAN = "SELECT sh.* FROM superHuman sh JOIN sighting s ON s.superHumanId = sh.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_SUPER_HUMAN, new SuperHumanMapper(), id);
    }

    @Override
    public List<Sighting> getSightingsForLocationById(int id) {
        final String GET_SIGHTING_BY_LOCATION_ID = "SELECT s.* FROM sighting s JOIN location l ON l.id = s.locationId WHERE l.id = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTING_BY_LOCATION_ID, new SightingMapper(), id);
        associateSuperHumansAndLocations(sightings);
        return sightings;
    }

    private void associateSuperHumansAndLocations(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setSuperHuman(getSuperHumanForSighting(sighting.getId()));
            sighting.getSuperHuman().setPowers(getPowersForSuperHuman(sighting.getSuperHuman().getId()));
        }
    }

    @Override
    public List<Sighting> getSightingsForDateById(String date) {
        LocalDate ld = LocalDate.parse(date);
        final String GET_SIGHTINGS_BY_DATE = "SELECT * FROM sighting WHERE date = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTINGS_BY_DATE, new SightingMapper(), ld);
        associateSuperHumansAndLocations(sightings);
        return sightings;
    } /// convert string to SQL date format YYYY-MM-DD

    @Override
    public List<Sighting> getSightingsForSuperHumanById(int id) {
        final String GET_SIGHTING_BY_SUPER_HUMAN_ID = "SELECT s.* FROM sighting s JOIN superHuman sh ON sh.id = s.superHumanId WHERE sh.id = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTING_BY_SUPER_HUMAN_ID, new SightingMapper(), id);
        associateSuperHumansAndLocations(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        associateSuperHumansAndLocations(sightings);
        return sightings;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = " UPDATE sighting SET name = ?, date = ?, description = ?, superHumanId = ?, locationId = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getName(),
                sighting.getDate(), // convert string to sql date format YYYY-MM-DD
                sighting.getDescription(),
                sighting.getSuperHuman().getId(),
                sighting.getLocation().getId(),
                sighting.getId());
    }

    @Override
    public void deleteSighting(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    private List<Power> getPowersForSuperHuman(int id) {
        final String GET_POWERS_FOR_SUPER_HUMAN = "SELECT p.* FROM power p JOIN super_human_power shp ON shp.powerId = p.id WHERE shp.superHumanId = ?";
        return jdbc.query(GET_POWERS_FOR_SUPER_HUMAN, new PowerDaoDB.PowerMapper(), id);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            LocalDate localDate = LocalDate.parse(rs.getString("date"));
            Sighting sighting = new Sighting();
            sighting.setName(rs.getString("name"));
            sighting.setId(rs.getInt("id"));
            sighting.setDate(localDate);
            sighting.setDescription(rs.getString("description"));
            return sighting;
        }
    }
}
