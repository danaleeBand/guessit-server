package com.danaleeband.guessit.game.dto;

import lombok.Getter;

@Getter
public class SubmitAnswerResponseDto {

    private long playerId;
    private long quizId;
    private long submitOrder;

    public SubmitAnswerResponseDto(long playerId, long quizId, long submitOrder) {
        this.playerId = playerId;
        this.quizId = quizId;
        this.submitOrder = submitOrder;
    }
}
