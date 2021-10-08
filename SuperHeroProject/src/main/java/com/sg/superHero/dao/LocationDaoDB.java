/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.dao.SightingDaoDB.SightingMapper;
import com.sg.superHero.model.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location (name, description, streetNumber, streetName, "
                + "city, state, zip, longitude, latitude) VALUES (?,?,?,?,?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getStreetNumber(),
                location.getStreetName(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLongitude(),
                location.getLatitude());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateLocation(Location location) {
        List<Sighting> sightings = getSightingsForLocation(location.getId());
        associateSuperHumansAndLocations(sightings);
        final String UPDATE_LOCATION = "UPDATE location Set name = ?, description = ?, streetNumber = ?, streetName = ?, "
                + "city = ?, state = ?, zip = ?, longitude = ?, latitude = ? WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getStreetNumber(),
                location.getStreetName(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLongitude(),
                location.getLatitude(),
                location.getId());

        final String DELETE_LOCATION_FROM_SIGHTING = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION_FROM_SIGHTING, location.getId());
        insertLocationForSightings(sightings, location);
    } // this is linked to the sighting table so I need another entry here

    private void insertLocationForSightings(List<Sighting> sightings, Location location) {
        final String INSERT_SIGHTINS_VIA_LOCATION = "INSERT INTO sighting (name, date, description, superHumanId, locationId) VALUES (?,?,?,?,?)";
        for (int i = 0; i < sightings.size(); i++) {
            jdbc.update(INSERT_SIGHTINS_VIA_LOCATION,
                    sightings.get(i).getName(),
                    sightings.get(i).getDate(),
                    sightings.get(i).getDescription(),
                    sightings.get(i).getSuperHuman().getId(),
                    location.getId());
        }
    }

    private void associateSuperHumansAndLocations(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setSuperHuman(getSuperHumanForSighting(sighting.getId()));
        }
    }

    private List<Sighting> getSightingsForLocation(int id) {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM sighting WHERE locationId = ?";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper(), id);
        return sightings;
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION = "SELECT l.* FROM location l JOIN sighting s ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION, new LocationMapper(), id);
    }

    private SuperHuman getSuperHumanForSighting(int id) {
        final String SELECT_SUPER_HUMAN = "SELECT sh.* FROM superHuman sh JOIN sighting s ON s.superHumanId = sh.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_SUPER_HUMAN, new SuperHumanDaoDB.SuperHumanMapper(), id);
    }

    @Override
    @Transactional
    public void deleteLocation(int id) {
        final String DELETE_SUPER_ORG = "DELETE shoa.* FROM super_human_organization_affiliation shoa "
                + "JOIN organization o ON shoa.organizationId = o.Id WHERE o.locationId = ?";
        jdbc.update(DELETE_SUPER_ORG, id);

        final String DELETE_ORGANIZATION_LOCATION = "DELETE FROM organization WHERE locationId = ?";
        jdbc.update(DELETE_ORGANIZATION_LOCATION, id);

        final String DELETE_LOCATION_SIGHTING = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION_SIGHTING, id);

        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setStreetNumber(rs.getString("streetNumber"));
            location.setStreetName(rs.getString("streetName"));
            location.setCity(rs.getString("city"));
            location.setState(rs.getString("state"));
            location.setZip(rs.getString("zip"));
            location.setDescription(rs.getString("description"));
            location.setLatitude(rs.getBigDecimal("latitude").stripTrailingZeros());
            location.setLongitude(rs.getBigDecimal("longitude").stripTrailingZeros());
            return location;
        }
    }
}
