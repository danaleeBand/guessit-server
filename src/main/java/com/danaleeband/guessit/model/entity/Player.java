package com.danaleeband.guessit.model.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("player")
public class Player {

    private String playerId;
    private String name;

    public Player() {
        this.playerId = UUID.randomUUID().toString(); // UUID 자동 생성
    }

    public Player(String name) {
        this.playerId = UUID.randomUUID().toString();
        this.name = name;
    }
}
