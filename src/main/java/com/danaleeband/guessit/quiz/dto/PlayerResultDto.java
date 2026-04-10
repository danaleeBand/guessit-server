package com.danaleeband.guessit.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerResultDto {

    private long playerId;
    private boolean isCorrect;
    private String submittedAnswer;
    private int rank;
    private int score;
}
