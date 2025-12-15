package com.danaleeband.guessit.controller.websocket.dto;

import lombok.Getter;

@Getter
public class PlayerReadyRequestDto {

    private Long roomId;
    private Long playerId;
}
