package com.danaleeband.guessit.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RoomJoinRequestDto {

    @NotNull
    private Long playerId;
    private String password;
}
