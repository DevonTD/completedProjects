/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.dao;

import com.sg.guessthenumberrest.dto.Game;
import com.sg.guessthenumberrest.dto.Round;
import com.sg.guessthenumberrest.testApplicationConfig;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    public GameDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }

    @AfterEach
    public void tearDown() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }

    /**
     * Test of createGame method, of class GameDaoDB.
     */
    @Test
    public void testCreateGetGame() {
        Game game = new Game();
        game.setGameAnswer("1234");
        game.setFinished(false);
        gameDao.createGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);

    }

    /**
     * Test of getAllGames method, of class GameDaoDB.
     */
    @Test
    public void testGetAllGames() {
        Game gameOne = new Game();
        gameOne.setGameAnswer("1234");
        gameOne.setFinished(false);
        gameDao.createGame(gameOne);

        Game gameTwo = new Game();
        gameTwo.setGameAnswer("4321");
        gameTwo.setFinished(false);
        gameDao.createGame(gameTwo);

        List<Game> games = gameDao.getAllGames();

        assertEquals(2, games.size());
        assertTrue(games.contains(gameOne));
        assertTrue(games.contains(gameTwo));

    }

    /**
     * Test of updateGameById method, of class GameDaoDB.
     */
    @Test
    public void testUpdate() {
        Game game = new Game();
        game.setGameAnswer("1234");
        game.setFinished(false);
        gameDao.createGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);

        game.setFinished(true);

        gameDao.updateGameById(game);

        assertNotEquals(game, fromDao);

        fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);
    }

}
