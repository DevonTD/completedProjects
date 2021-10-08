/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.dao.LocationDaoDB.LocationMapper;
import com.sg.superHero.dao.SuperHumanDaoDB.SuperHumanMapper;
import com.sg.superHero.model.Location;
import com.sg.superHero.model.Organization;
import com.sg.superHero.model.Power;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization addOrg(Organization organization) {
        final String INSERT_ORG = " INSERT INTO organization (name, description, phone, email, hero, locationId) VALUES (?,?,?,?,?,?)";
        jdbc.update(INSERT_ORG,
                organization.getName(),
                organization.getDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.isHero(),
                organization.getLocation().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        insertSuperHumanOrgAffiliation(organization);
        return organization;
    }

    private void insertSuperHumanOrgAffiliation(Organization org) {
        final String INSERT_INTO_ORG_AFFILIATION = "INSERT INTO super_human_organization_affiliation "
                + "(superHumanId, organizationId) VALUES(?,?)";
        for (SuperHuman superHuman : org.getSuperHumans()) {
            jdbc.update(INSERT_INTO_ORG_AFFILIATION,
                    superHuman.getId(),
                    org.getId());
        }
    }

    @Override
    public Organization getOrgById(int id) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM organization WHERE id = ?";
            Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganizationMapper(), id);
            org.setSuperHumans(getSuperHumansForOrg(id));
            org.setLocation(getLocationForOrganization(id));
            givePowers(org.getSuperHumans());
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForOrganization(int id) {
        final String SELECT_LOCATION_FOR_ORG = "SELECT l.* FROM location l "
                + "JOIN organization o ON o.locationId = l.id WHERE o.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_ORG, new LocationMapper(), id);
    }

    @Override
    public List<SuperHuman> getSuperHumansForOrg(int id) {
        final String SELECT_SUPER_HUMANS_FOR_ORG = "SELECT sh.* FROM SuperHuman sh JOIN super_human_organization_affiliation shoa "
                + "ON shoa.superHumanId = sh.id WHERE shoa.organizationId = ?";
        List<SuperHuman> superHumans = jdbc.query(SELECT_SUPER_HUMANS_FOR_ORG, new SuperHumanMapper(), id);
        givePowers(superHumans);
        return superHumans;
    }

    @Override
    public List<Organization> getAllOrgsForSuperHuman(int id) {
        final String SELECT_ALL_ORGS_FOR_SUPER_HUMAN = "SELECT org.* FROM organization org JOIN super_human_organization_affiliation shoa "
                + "ON shoa.organizationId = org.id WHERE shoa.superHumanId = ?";
        List<Organization> orgs = jdbc.query(SELECT_ALL_ORGS_FOR_SUPER_HUMAN, new OrganizationMapper(), id);
        associateSupersAndLocation(orgs);
        return orgs;
    }

    @Override
    public List<Organization> getAllOrgs() {
        final String GET_ALL_ORGS = "SELECT * FROM organization";
        List<Organization> orgs = jdbc.query(GET_ALL_ORGS, new OrganizationMapper());
        associateSupersAndLocation(orgs);
        return orgs;
    }

    private void associateSupersAndLocation(List<Organization> orgs) {
        for (Organization org : orgs) {
            org.setLocation(getLocationForOrganization(org.getId()));
            org.setSuperHumans(getSuperHumansForOrg(org.getId()));
        }
    }

    @Override
    @Transactional
    public void updateOrg(Organization organization) {
        final String UPDATE_ORG = "UPDATE organization SET name = ?, description = ?, phone = ?, email = ?, hero = ?, locationId = ? WHERE id = ?";
        jdbc.update(UPDATE_ORG,
                organization.getName(),
                organization.getDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.isHero(),
                organization.getLocation().getId(),
                organization.getId());

        final String DELETE_ORG_AFFILITATION = "DELETE FROM super_human_organization_affiliation WHERE organizationId = ?";
        jdbc.update(DELETE_ORG_AFFILITATION, organization.getId());
        insertSuperHumanOrgAffiliation(organization);
    }

    @Override
    @Transactional
    public void deleteOrg(int id) {
        final String DELETE_SUPER_HUMAN_ORG_AFFILIATION = "DELETE FROM super_human_organization_affiliation WHERE organizationId = ?";
        jdbc.update(DELETE_SUPER_HUMAN_ORG_AFFILIATION, id);

        final String DELETE_ORG = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORG, id);
    }

    private void givePowers(List<SuperHuman> superHumans) {
        for (SuperHuman superHuman : superHumans) {
            superHuman.setPowers(getPowersForSuperHuman(superHuman.getId()));
        }
    }

    private List<Power> getPowersForSuperHuman(int id) {
        final String GET_POWERS_FOR_SUPER_HUMAN = "SELECT p.* FROM power p JOIN super_human_power shp ON shp.powerId = p.id WHERE shp.superHumanId = ?";
        return jdbc.query(GET_POWERS_FOR_SUPER_HUMAN, new PowerDaoDB.PowerMapper(), id);
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setPhone(rs.getString("phone"));
            org.setEmail(rs.getString("email"));
            org.setHero(rs.getBoolean("hero"));
            return org;
        }
    }
}
