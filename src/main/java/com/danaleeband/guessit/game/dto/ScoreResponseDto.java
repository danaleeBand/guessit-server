package com.danaleeband.guessit.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScoreResponseDto {

    private long playerId;
    private int score;
    private int rank;
}
