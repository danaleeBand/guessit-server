package com.danaleeband.guessit.model.entity;

import com.danaleeband.guessit.domain.GAME_STATUS;
import com.danaleeband.guessit.domain.Player;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("room")
public class Room {

    @Id
    private int id;
    private GAME_STATUS status;
    private List<Player> players;
    private int quizId;

    public Room(int id, GAME_STATUS status, int quizId) {
        this.id = id;
        this.status = status;
        this.players = new ArrayList<>();
        this.quizId = quizId;
    }
}
