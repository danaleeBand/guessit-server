package com.danaleeband.guessit.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RoomCreateRequestDto {

    @NotBlank
    @Size(max = 25)
    private String title;

    private String password;

    @NotNull
    private boolean locked;

    @NotNull
    private Long creatorId;
}
