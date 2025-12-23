package com.danaleeband.guessit.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomLeaveResponseDto {

    private boolean success;
    private String message;

    public static RoomLeaveResponseDto success() {
        return new RoomLeaveResponseDto(true, "방에서 나갔습니다.");
    }

    public static RoomLeaveResponseDto fail(String message) {
        return new RoomLeaveResponseDto(false, message);
    }
}
