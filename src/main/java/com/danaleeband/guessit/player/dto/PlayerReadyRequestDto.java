package com.danaleeband.guessit.player.dto;

import lombok.Getter;

@Getter
public class PlayerReadyRequestDto {

    private Long roomId;
    private Long playerId;
}
