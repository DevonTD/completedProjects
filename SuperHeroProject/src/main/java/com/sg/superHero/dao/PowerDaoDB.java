/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superHero.dao;

import com.sg.superHero.dao.SuperHumanDaoDB.SuperHumanMapper;
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
public class PowerDaoDB implements PowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power getPowerById(int id) {
        try{
            final String GET_POWER_BY_ID = "SELECT * FROM power WHERE id = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID, new PowerMapper(), id);
        }catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        final String GET_ALL_POWERS = "SELECT * FROM power";
        return jdbc.query(GET_ALL_POWERS, new PowerMapper());
    }

    @Override
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO power (name, description) VALUES (?,?)";
        jdbc.update(INSERT_POWER,
                power.getName(),
                power.getDescription());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }

    @Override
    @Transactional
    public void updatePower(Power power) {
        List<SuperHuman> superHumans = getSuperHumansWithPower(power.getId());
        final String UPDATE_POWER = "UPDATE power SET name = ?, description = ? WHERE id = ?";
        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getDescription(),
                power.getId());
        
        final String DELETE_FROM_SUPER_HUMAN = "DELETE FROM super_human_power sp WHERE sp.powerId= ?";
        jdbc.update(DELETE_FROM_SUPER_HUMAN, power.getId());
        insertSuperHumanPower(superHumans, power);
    }

    public void insertSuperHumanPower(List<SuperHuman> superHumans, Power power){
        final String INSERT_SUPER_POWERS = "INSERT INTO super_human_power (superHumanId, powerId) VALUES (?,?)";
        for(int i = 0; i < superHumans.size(); i++){
            jdbc.update(INSERT_SUPER_POWERS,
                    superHumans.get(i).getId(),
                    power.getId());
        }
    }
    
    public List<SuperHuman> getSuperHumansWithPower(int id){
        final String GET_POWERS = "SELECT sh.* FROM superHuman sh JOIN super_human_power shp ON shp.superHumanId = sh.id WHERE shp.powerId = ?";
        List<SuperHuman> superHumans = jdbc.query(GET_POWERS, new SuperHumanMapper(), id);
        return superHumans;
    }
    
    @Override
    @Transactional
    public void deletePower(int id) {
        final String DELETE_POWER_SUPER_HUMAN = "DELETE FROM super_human_power shp WHERE shp.powerId= ?";
        jdbc.update(DELETE_POWER_SUPER_HUMAN, id);
        
        final String DELETE_POWER = "DELETE FROM power WHERE id = ?";
        jdbc.update(DELETE_POWER, id);
    }

    public static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int index) throws SQLException {
            Power power = new Power();
            power.setId(rs.getInt("id"));
            power.setName(rs.getString("name"));
            power.setDescription(rs.getString("description"));
            return power;
        }
    }
}
