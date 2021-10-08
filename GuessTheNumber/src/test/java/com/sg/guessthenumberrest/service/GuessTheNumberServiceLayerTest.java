/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.service;

import com.sg.guessthenumberrest.dto.Game;
import com.sg.guessthenumberrest.dto.Round;
import com.sg.guessthenumberrest.testApplicationConfig;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
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
public class GuessTheNumberServiceLayerTest {

    @Autowired
    GuessTheNumberService service;

    public GuessTheNumberServiceLayerTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Game> games = service.allGames();
        for (Game game : games) {
            service.deleteRoundById(game.getGameId());
        }
        for (Game game : games) {
            service.deleteGameById(game.getGameId());
        }
    }

    @AfterEach
    public void tearDown() {
        List<Game> games = service.allGames();
        for (Game game : games) {
            service.deleteRoundById(game.getGameId());
        }
        for (Game game : games) {
            service.deleteGameById(game.getGameId());
        }
    }

    /**
     * Test of startGame method, of class GuessTheNumberServiceLayer.
     */
    @Test
    public void testStartGameGetGameById() {
        Game game = new Game();
        service.startGame(game);

        Game fromDao = service.gameById(game.getGameId());

        assertEquals(game, fromDao);
    }

    /**
     * Test of guess method, of class GuessTheNumberServiceLayer.
     */
    @Test
    public void testGuess() {
        Game game = new Game();
        service.startGame(game);

        // Test one round and add it to the list of rounds in the game object
        Round round = new Round();
        round.setGuess("1234");
        service.guess(round, game);
        assertEquals(game.getRounds().size(), 1);
        assertTrue(game.getRounds().contains(round));

        // Create second round and add it to the list again
        Round round2 = new Round();
        round2.setGuess("9136");
        service.guess(round2, game);
        assertEquals(game.getRounds().size(), 2);
        assertTrue(game.getRounds().contains(round));
        assertTrue(game.getRounds().contains(round2));
    }

    /**
     * Test of allGames method, of class GuessTheNumberServiceLayer.
     */
    @Test
    public void testGamesList() {
        Game game1 = new Game();
        service.startGame(game1);

        Game gam = new Game();
        service.startGame(gam);

        Game ga = new Game();
        service.startGame(ga);

        Game g = new Game();
        service.startGame(g);

        List<Game> games = service.allGames();

        assertEquals(games.size(), 4);
        assertTrue(games.contains(game1));
        assertTrue(games.contains(gam));
        assertTrue(games.contains(ga));
        assertTrue(games.contains(g));
    }

    /**
     * Test of getAllroundsByGameId method, of class GuessTheNumberServiceLayer.
     */
    @Test
    public void testGetRoundsByGame() {
        // Game  1 with 2 rounds
        Game game = new Game();
        service.startGame(game);

        Round round = new Round();
        round.setGuess("1234");
        service.guess(round, game);

        Round round2 = new Round();
        round2.setGuess("9136");
        service.guess(round2, game);

        // Game 2 with 3 rounds
        Game g = new Game();
        service.startGame(g);

        Round r = new Round();
        r.setGuess("1793");
        service.guess(r, g);

        Round ro = new Round();
        ro.setGuess("3412");
        service.guess(ro, g);

        Round whatever = new Round();
        whatever.setGuess("9615");
        service.guess(whatever, g);

        List<Round> rounds = service.getAllroundsByGameId(game); 
        List<Round> roundz = service.getAllroundsByGameId(g);
        

        assertEquals(game.getRounds().size(), rounds.size());
        assertEquals(g.getRounds().size(), roundz.size());

    }

}
