package com.danaleeband.guessit.game;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerScore implements Serializable {

    private long playerId;
    private int score;
    @Setter
    private int rank;

    public PlayerScore() {
    }

    public PlayerScore(long playerId) {
        this.playerId = playerId;
        this.score = 0;
        this.rank = 0;
    }

    public void addScore(int value) {
        this.score += value;
    }

}
