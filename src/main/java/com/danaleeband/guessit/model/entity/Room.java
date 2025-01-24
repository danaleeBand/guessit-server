package com.danaleeband.guessit.model.entity;

import com.danaleeband.guessit.domain.GAME_STATUS;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("room")
public class Room implements Serializable {

    @Id
    private String id;
    private String code;
    private String title;
    private GAME_STATUS status;
    private Boolean locked;
    private String password;
    private List<Player> players;
    private List<Long> quizIds;
    private Long quizId;

    public Room() {
    }

    public Room(String id, String code, String title, Boolean locked, String password,
        List<Player> players,
        List<Long> quizIds) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.status = GAME_STATUS.WAITING;
        this.locked = locked;
        this.password = password;
        this.players = players;
        this.quizIds = quizIds;
        this.quizId = quizIds.get(0);
    }
}
