package com.danaleeband.guessit.domain;

public enum GAME_STATUS {
    PLAYING("게임중"),
    WAITING("대기중");

    private final String description;

    private GAME_STATUS(String description) {
        this.description = description;
    }

}
