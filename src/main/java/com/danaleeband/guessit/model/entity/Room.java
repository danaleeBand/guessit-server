package com.danaleeband.guessit.model.entity;

import com.danaleeband.guessit.domain.GAME_STATUS;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("room")
public class Room {

    @Id
    private String id;
    private String title;
    private GAME_STATUS status;
    private List<Player> players;
    private List<Long> quizIds;
    private Long quizId;

    public Room(String id, String title, GAME_STATUS status, List<Player> players, List<Long> quizIds, Long quizId) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.players = players;
        this.quizIds = quizIds;
        this.quizId = quizId;
    }
}
