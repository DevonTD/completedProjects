/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.dao;

import com.sg.guessthenumberrest.dto.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author devon
 */
@Repository
public class RoundDaoDB implements RoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoundDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Round createRound(Round round) {
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
        round.setTimeOfGuess(time);

        final String sql = "INSERT INTO round(guess, timeOfGuess, exactMatch, partialMatch, gameId) "
                + "VALUES(?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder(); // Used to contain the auto-generated key

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, round.getGuess());
            statement.setString(2, round.getTimeOfGuess()); // tf
            statement.setInt(3, round.getExactMatch());
            statement.setInt(4, round.getPartialMatch());
            statement.setInt(5, round.getGameId());
            return statement;
        }, keyHolder);
        round.setRoundId(keyHolder.getKey().intValue());
        return round;
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round r = new Round();
            r.setRoundId(rs.getInt("Id"));
            r.setGuess(rs.getString("guess"));
            r.setTimeOfGuess(rs.getString("timeOfGuess")); // check meetingDaoDB
            r.setExactMatch(rs.getInt("exactMatch"));
            r.setPartialMatch(rs.getInt("partialMatch"));
            r.setGameId(rs.getInt("gameId"));
            return r;
        }
    }
}
