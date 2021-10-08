/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.dao;

import com.sg.guessthenumberrest.dto.Game;
import com.sg.guessthenumberrest.dto.Round;
import com.sg.guessthenumberrest.testApplicationConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
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
@SpringBootTest(classes = testApplicationConfig.class)
public class RoundDaoDBTest {

    @Autowired
    RoundDao roundDao;
    @Autowired
    GameDao gameDao;

    public RoundDaoDBTest() {
    }

    /**
     * Test of createRound method, of class RoundDaoDB.
     */
    @Test
    public void testCreateRoundAndGetRoundsByGameId() {

        // Create & Fill ojbect values
        Game game = new Game();
        game.setGameAnswer("1234");
        game.setFinished(false);
        gameDao.createGame(game);
        
        Game gameTwo = new Game();
        gameTwo.setGameAnswer("5654");
        gameTwo.setFinished(false);
        gameDao.createGame(gameTwo);

        // Create rounds and tie to game Id
        Round roundOne = new Round();
        roundOne.setExactMatch(0);
        roundOne.setGuess("5678");
        roundOne.setPartialMatch(0);
        roundOne.setGameId(game.getGameId());
        roundDao.createRound(roundOne);

        Round roundTwo = new Round();
        roundTwo.setExactMatch(0);
        roundTwo.setPartialMatch(0);
        roundTwo.setGuess("5484");
        roundTwo.setGameId(game.getGameId());
        roundDao.createRound(roundTwo);
        
        Round round = new Round();
        round.setExactMatch(1);
        round.setGuess("5654");
        round.setPartialMatch(0);
        round.setGameId(gameTwo.getGameId());
        roundDao.createRound(round);
        

        // insert objects into db
        Game fromDao = gameDao.getGameById(game.getGameId());
        Game fromDaoTwo = gameDao.getGameById(gameTwo.getGameId());
        assertEquals(game, fromDao);
        assertEquals(gameTwo, fromDaoTwo);

    }
}
