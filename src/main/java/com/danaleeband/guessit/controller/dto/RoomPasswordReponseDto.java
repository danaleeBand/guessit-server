package com.danaleeband.guessit.controller.dto;

import lombok.Getter;

@Getter
public class RoomPasswordReponseDto {

    private boolean valid;
    private String message;

    private RoomPasswordReponseDto(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static RoomPasswordReponseDto getValidResponse() {
        return new RoomPasswordReponseDto(true, null);
    }

    public static RoomPasswordReponseDto getInvalidResponse() {
        return new RoomPasswordReponseDto(false, "Invalid password");
    }
}
