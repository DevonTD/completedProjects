/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.service;

import com.sg.guessthenumberrest.dto.Game;
import com.sg.guessthenumberrest.dto.Round;
import java.util.List;

/**
 *
 * @author devon
 */
public interface GuessTheNumberService {
    void startGame(Game game);
    void guess(Round round, Game game);
    List<Game> allGames();
    Game gameById(int id);
    List<Round> getAllroundsByGameId(Game game);
    void deleteRoundById(int id);
    void deleteGameById(int id);
}
