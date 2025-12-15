package com.danaleeband.guessit.player;

import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("player")
public class Player implements Serializable {

    @Id
    private long id;
    private String nickname;
    private String profileUrl;
    @Setter
    private Boolean isReady;

    public Player() {
    }

    public Player(String nickname, String profileUrl) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.isReady = false;
    }

    public void assignId(Long id) {
        this.id = id;
    }

}
