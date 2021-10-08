/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.dto;

import java.util.Objects;

/**
 *
 * @author devon
 */

public class Round {
    int roundId;
    String guess;
    String timeOfGuess;
    int exactMatch;
    int partialMatch;
    int gameId;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getTimeOfGuess() {
        return timeOfGuess;
    }

    public void setTimeOfGuess(String timeOfGuess) {
        this.timeOfGuess = timeOfGuess;
    }

    public int getExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(int exactMatch) {
        this.exactMatch = exactMatch;
    }

    public int getPartialMatch() {
        return partialMatch;
    }

    public void setPartialMatch(int partialMatch) {
        this.partialMatch = partialMatch;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.roundId;
        hash = 89 * hash + Objects.hashCode(this.guess);
        hash = 89 * hash + Objects.hashCode(this.timeOfGuess);
        hash = 89 * hash + this.exactMatch;
        hash = 89 * hash + this.partialMatch;
        hash = 89 * hash + this.gameId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.exactMatch != other.exactMatch) {
            return false;
        }
        if (this.partialMatch != other.partialMatch) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.timeOfGuess, other.timeOfGuess)) {
            return false;
        }
        return true;
    }
    
}
