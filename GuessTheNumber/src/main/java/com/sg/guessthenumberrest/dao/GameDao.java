/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.dao;

import com.sg.guessthenumberrest.dto.Game;
import java.util.List;

/**
 *
 * @author devon
 */
public interface GameDao { 
    Game createGame(Game game);
    List<Game> getAllGames();
    Game getGameById(int id);
    void updateGameById(Game game);
    void deleteGameById(int id);
}
