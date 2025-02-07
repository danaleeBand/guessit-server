package com.danaleeband.guessit.model.entity;

import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("player")
public class Player implements Serializable {

    @Id
    private Long playerId;
    private String nickname;
    private String profileUrl;

    public Player() {
    }

    public Player(String nickname, String profileUrl) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }

    public void assignId(Long playerId) {
        this.playerId = playerId;
    }
}
