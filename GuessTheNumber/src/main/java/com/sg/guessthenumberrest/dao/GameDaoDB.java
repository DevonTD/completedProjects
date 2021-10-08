/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.dao;

import com.sg.guessthenumberrest.dto.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author devon
 */
@Repository
public class GameDaoDB implements GameDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game createGame(Game game) {
        // Retrieves auto_incremented pk from the DB and sets the value of the game ID to that value
        final String sql = "INSERT INTO game(gameAnswer) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder(); // Used to contain the auto-generated key

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, game.getGameAnswer());
            return statement;
        }, keyHolder);
        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    } // Can i omit game answers in the SQL query while still showing everything in table?

    @Override
    public Game getGameById(int id) {
        try {
            final String sql = "SELECT * FROM game WHERE id = ?;";
            return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateGameById(Game game) {
        final String sql = "UPDATE game SET finished = ? WHERE id = ?;";
        jdbcTemplate.update(sql, game.getFinished(), game.getGameId());
    }

    @Override
    public void deleteGameById(int id) {
        final String sql = "DELETE FROM game WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game g = new Game();
            g.setGameId(rs.getInt("id"));
            g.setGameAnswer(rs.getString("gameAnswer"));
            g.setFinished(rs.getBoolean("finished"));
            return g;
            
            
        }
    }
}
