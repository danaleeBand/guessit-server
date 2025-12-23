package com.danaleeband.guessit.room.dto;

import lombok.Getter;

@Getter
public class RoomJoinResponseDto {

    private boolean valid;
    private String message;

    private RoomJoinResponseDto(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static RoomJoinResponseDto getValidResponse() {
        return new RoomJoinResponseDto(true, null);
    }

    public static RoomJoinResponseDto getInvalidPasswordResponse() {
        return new RoomJoinResponseDto(false, "🔒 비밀번호를 확인해주세요");
    }

    public static RoomJoinResponseDto getFullRoomResponse() {
        return new RoomJoinResponseDto(false, "⚠️ 방이 가득 찼습니다");
    }

    public static RoomJoinResponseDto getPlayerDuplicatedResponse() {
        return new RoomJoinResponseDto(false, "⚠️ 이미 참여하고 있는 방입니다");
    }
}
