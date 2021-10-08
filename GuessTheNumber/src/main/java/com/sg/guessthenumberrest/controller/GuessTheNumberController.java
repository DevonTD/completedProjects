/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.controller;

import com.sg.guessthenumberrest.dto.Game;
import com.sg.guessthenumberrest.dto.Round;
import com.sg.guessthenumberrest.service.ErrorHandler;
import com.sg.guessthenumberrest.service.GuessTheNumberService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author devon
 */
@RestController
@RequestMapping("/api")
public class GuessTheNumberController {

    @Autowired
    GuessTheNumberService service;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Game> createGame() {
        Game game = new Game();
        service.startGame(game);
        game.setGameAnswer("");
        return ResponseEntity.ok(game);
    }

    @PostMapping("/guess")
    public ResponseEntity<Game> play(int id, String guess) {
        Game game = service.gameById(id);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        Round round = new Round();
        round.setGuess(guess);
        for (char c : round.getGuess().toCharArray()) {
            if (!Character.isDigit(c)) {
                return new ResponseEntity(guess, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        service.guess(round, game);
        if (game.getFinished() == false) {
            game.setGameAnswer("");
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        List<Game> games = service.allGames();
        List<Game> inProgress = new ArrayList<>();
        for (Game game : games) {
            if (game.getFinished() == false) {
                game.setGameAnswer("");
                inProgress.add(game);
            } else {
                inProgress.add(game);
            }
        }
        return inProgress;

    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int id) {
        Game game = service.gameById(id);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        if (game.getFinished() == false) {
            game.setGameAnswer("");
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/rounds/{gameId}")
    public ResponseEntity<List<Round>> getRoundsByGame(@PathVariable int gameId) {
        Game game = service.gameById(gameId);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        List<Round> rounds = service.getAllroundsByGameId(game);
        return ResponseEntity.ok(rounds);
    }
}
