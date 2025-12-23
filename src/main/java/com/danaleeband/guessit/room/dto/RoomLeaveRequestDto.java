package com.danaleeband.guessit.room.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomLeaveRequestDto {

    @NotNull
    private Long roomId;

    @NotNull
    private Long playerId;
}
