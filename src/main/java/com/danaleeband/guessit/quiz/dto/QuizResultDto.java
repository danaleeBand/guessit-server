package com.danaleeband.guessit.quiz.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizResultDto {

    private int quizOrder;
    private long quizId;
    private List<PlayerResultDto> results;
    private String answer;
}
