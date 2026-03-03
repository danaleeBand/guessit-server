package com.danaleeband.guessit.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HintResponseDto {

    private int quizOrder;
    private long quizId;
    private String hint;
    private int answerLength;
}
