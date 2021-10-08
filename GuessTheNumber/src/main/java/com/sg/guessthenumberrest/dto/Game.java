/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumberrest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author devon
 */
public class Game {
    int gameId;
    String gameAnswer;
    Boolean finished;
    List<Round> Rounds = new ArrayList<>();

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameAnswer() {
        return gameAnswer;
    }

    public void setGameAnswer(String gameAnswer) {
        this.gameAnswer = gameAnswer;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public List<Round> getRounds() {
        return Rounds;
    }

    public void setRounds(List<Round> Rounds) {
        this.Rounds = Rounds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.gameId;
        hash = 11 * hash + Objects.hashCode(this.gameAnswer);
        hash = 11 * hash + Objects.hashCode(this.finished);
        hash = 11 * hash + Objects.hashCode(this.Rounds);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.gameAnswer, other.gameAnswer)) {
            return false;
        }
        if (!Objects.equals(this.finished, other.finished)) {
            return false;
        }
        return Objects.equals(this.Rounds, other.Rounds);
    }


}
