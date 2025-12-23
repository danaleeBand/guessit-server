package com.danaleeband.guessit.room.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomSessionBindRequestDto {

    @NotNull
    private Long roomId;

    @NotNull
    private Long playerId;
}
