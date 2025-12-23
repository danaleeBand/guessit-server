package com.danaleeband.guessit.room.dto;

import lombok.Getter;

@Getter
public class GameReadyRequestDto {

    private long roomId;
    private long playerId;
    private boolean ready;
}
