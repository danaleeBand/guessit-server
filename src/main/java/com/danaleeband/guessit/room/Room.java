package com.danaleeband.guessit.room;

import static com.danaleeband.guessit.global.Constants.PLAYER_LIMIT;

import com.danaleeband.guessit.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    private void changeCreatorIfNeeded(Player leavingPlayer) {
        if (this.creator == null || !this.creator.equals(leavingPlayer)) {
            return;
        }

        if (this.players.isEmpty()) {
            this.creator = null;
            return;
        }

        this.creator = this.players.get(0);
    }

    public void leave(Player player) {
        removePlayer(player);
        changeCreatorIfNeeded(player);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.players.isEmpty();
    }
}
