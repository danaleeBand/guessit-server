package com.danaleeband.guessit.websocket.dto;

import com.danaleeband.guessit.entity.Player;
import com.danaleeband.guessit.entity.Room;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomDetailSocketDto implements Serializable {

    private long id;
    private String code;
    private String title;
    private boolean playing;
    private boolean locked;
    private Player creator;
    private List<Player> players;

    public static RoomDetailSocketDto toRoomDetailSocketDto(Room room) {
        return new RoomDetailSocketDto(
            room.getId(),
            room.getCode(),
            room.getTitle(),
            room.isPlaying(),
            room.isLocked(),
            room.getCreator(),
            room.getPlayers()
        );
    }
}
