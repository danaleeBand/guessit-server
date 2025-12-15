package com.danaleeband.guessit.controller.websocket.dto;

import com.danaleeband.guessit.entity.Room;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomSocketDto implements Serializable {

    private long id;
    private String code;
    private String title;
    private boolean playing;
    private boolean locked;
    private int playerCount;

    public static RoomSocketDto toRoomSocketDto(Room room) {
        return new RoomSocketDto(
            room.getId(),
            room.getCode(),
            room.getTitle(),
            room.isPlaying(),
            room.isLocked(),
            room.getPlayers().size()
        );
    }
}
