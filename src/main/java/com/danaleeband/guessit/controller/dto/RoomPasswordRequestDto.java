package com.danaleeband.guessit.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RoomPasswordRequestDto {

    @NotBlank
    private String password;
}
