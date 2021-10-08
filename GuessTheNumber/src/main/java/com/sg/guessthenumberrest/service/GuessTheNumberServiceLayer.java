/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sg.guessthenumberrest.dao.GameDao;
import com.sg.guessthenumberrest.dao.RoundDao;
import com.sg.guessthenumberrest.dao.RoundDaoDB;
import com.sg.guessthenumberrest.dto.Game;
import com.sg.guessthenumberrest.dto.Round;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author devon
 */
@Repository
public class GuessTheNumberServiceLayer implements GuessTheNumberService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    @Autowired
    public GuessTheNumberServiceLayer(GameDao gameDao, RoundDao roundDao, JdbcTemplate jdbcTemplate) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void startGame(Game game) {
        ArrayList<Integer> numbers = new ArrayList<>();

        while (numbers.size() < 4) {
            int random = (int) (Math.random() * 9 + 1);

            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        ArrayList<Integer> list = numbers;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }

        String answer = sb.toString();
        game.setGameAnswer(answer);
        game.setFinished(false);

        gameDao.createGame(game);
    }

    @Override
    public void guess(Round round, Game game) {
        // Takes the games generated answer and the user's guess and breaks them down into character array's. Limit's user guessto 4 digits
        round.setGameId(game.getGameId());
        char[] gameAnswer = characterBreakDown(game.getGameAnswer());
        char[] roundGuess = new char[4];
        roundGuess = characterBreakDown(round.getGuess());

        // used for comparing the array elements one by one
        int min = 0;
        int max = 1;

        int exact = 0;
        boolean run = true;
        while (run) {
            for (int i = 0; i <= 3; i++) {
                if (Arrays.equals(roundGuess, gameAnswer)) {
                    round.setExactMatch(4);
                    game.setFinished(true);
                    gameDao.updateGameById(game);
                    run = false;
                } else if (Arrays.equals(roundGuess, min, max, gameAnswer, min, max)) {
                    exact++;
                    round.setExactMatch(exact);
                    min++;
                    max++;
                    if (i == 3 && round.getExactMatch() >= 2) {
                        round.setPartialMatch(round.getExactMatch());
                        run = false;
                    } else if (i == 3) {
                        run = false;
                    }
                } else if (!Arrays.equals(roundGuess, min, max, gameAnswer, min, max)) {
                    min++;
                    max++;
                    if (i == 3 && round.getExactMatch() >= 2) {
                        round.setPartialMatch(round.getExactMatch());
                        run = false;
                    } else if (i == 3) {
                        run = false;
                    }
                }
            }
        }
        game.getRounds().add(round);
        roundDao.createRound(round);
    }

    @Override
    public List<Game> allGames() {
        List<Game> games = gameDao.getAllGames();
        return games;
    }

    @Override
    public Game gameById(int id) {
        Game game = gameDao.getGameById(id);
        return game;
    }

    @Override
    public List<Round> getAllroundsByGameId(Game game) {
        final String sql = "SELECT * FROM round WHERE gameId = ? ORDER BY timeOfGuess DESC;";
        return jdbcTemplate.query(sql, new RoundDaoDB.RoundMapper(), game.getGameId());
    }

    private static char[] characterBreakDown(String string) {
        String[] guessSplit = string.split("(?!^)");
        return Arrays.stream(guessSplit)
                .collect(Collectors.joining())
                .toCharArray();
    }

    @Override
    public void deleteRoundById(int id) {
        final String sql = "DELETE FROM round WHERE gameId = ?;";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteGameById(int id) {
        final String sql = "DELETE FROM game WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
