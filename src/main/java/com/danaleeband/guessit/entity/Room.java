package com.danaleeband.guessit.entity;

import static com.danaleeband.guessit.global.Constants.PLAYER_LIMIT;

import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("room")
public class Room implements Serializable {

    @Id
    private long id;
    private String code;
    private String title;
    private boolean playing;
    private boolean locked;
    private String password;
    private Player creator;
    private List<Player> players;
    private List<Long> quizIds;
    private Long quizId;

    public Room() {
    }

    public Room(String code, String title, boolean locked, String password,
        Player creator, List<Player> players, List<Long> quizIds) {
        this.code = code;
        this.title = title;
        this.playing = false;
        this.locked = locked;
        this.password = password;
        this.creator = creator;
        this.players = players;
        this.quizIds = quizIds;
        this.quizId = quizIds.get(0);
    }

    public void assignId(long id) {
        this.id = id;
    }

    public void addPlayer(Player player) {
        players.add(player);

        if (players.size() == PLAYER_LIMIT) {
            this.playing = true;
        }
    }
}
