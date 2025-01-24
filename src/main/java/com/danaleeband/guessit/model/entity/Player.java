package com.danaleeband.guessit.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("player")
public class Player {

    private String playerId;
    private String name;

    public Player(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }
}
