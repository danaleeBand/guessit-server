package com.danaleeband.guessit.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResponseDto {

    private Long id;
    private String answer;
    private String hint1;
    private String hint2;
    private String hint3;
    private String hint4;
    private String hint5;
    private String hint6;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
