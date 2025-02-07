package com.danaleeband.guessit.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PlayerCreateDTO {

    @NotBlank
    private String nickname;

    @NotBlank
    private String profileUrl;
}
