package com.danaleeband.guessit.controller.dto;

import lombok.Getter;

@Getter
public class RoomJoinReponseDto {

    private boolean valid;
    private String message;

    private RoomJoinReponseDto(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static RoomJoinReponseDto getValidResponse() {
        return new RoomJoinReponseDto(true, null);
    }

    public static RoomJoinReponseDto getInvalidPasswordResponse() {
        return new RoomJoinReponseDto(false, "🔒비밀번호를 확인해주세요");
    }

    public static RoomJoinReponseDto getFullRoomResponse() {
        return new RoomJoinReponseDto(false, "⚠️방이 가득 찼습니다");
    }
}
