package com.danaleeband.guessit.controller.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizCreateDto {

    @NotBlank
    @Size(max = 25)
    private String answer;

    @NotBlank
    @Size(max = 25)
    private String hint1;

    @NotBlank
    @Size(max = 25)
    private String hint2;

    @NotBlank
    @Size(max = 25)
    private String hint3;

    @NotBlank
    @Size(max = 25)
    private String hint4;

    @NotBlank
    @Size(max = 25)
    private String hint5;

    @NotBlank
    @Size(max = 25)
    private String hint6;
}
