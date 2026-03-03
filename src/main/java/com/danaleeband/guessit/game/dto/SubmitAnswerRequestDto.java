package com.danaleeband.guessit.game.dto;

import lombok.Getter;

@Getter
public class SubmitAnswerRequestDto {

    private long playerId;
    private long quizId;
    private String answer;
}
