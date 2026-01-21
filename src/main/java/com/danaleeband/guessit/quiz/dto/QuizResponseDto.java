package com.danaleeband.guessit.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizResponseDto {

    private Long id;
    private String answer;
    private String hint1;
    private String hint2;
    private String hint3;
    private String hint4;
    private String hint5;
    private String hint6;
    private String createdAt;
    private String updatedAt;
}
