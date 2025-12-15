package com.danaleeband.guessit.room;

import static com.danaleeband.guessit.global.Constants.PLAYER_LIMIT;

import com.danaleeband.guessit.player.Player;
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

    public Room() {
    }

    public Room(String code, String title, boolean locked, String password,
        Player creator, List<Player> players) {
        this.code = code;
        this.title = title;
        this.playing = false;
        this.locked = locked;
        this.password = password;
        this.creator = creator;
        this.players = players;
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
