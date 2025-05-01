package com.danaleeband.guessit.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PlayerCreateRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String profileUrl;
}
