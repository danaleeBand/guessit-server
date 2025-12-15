package com.danaleeband.guessit.room.dto;

import com.danaleeband.guessit.room.Room;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomDto implements Serializable {

    private long id;
    private String code;
    private String title;
    private boolean playing;
    private boolean locked;
    private int playerCount;

    public static RoomDto toRoomDto(Room room) {
        return new RoomDto(
            room.getId(),
            room.getCode(),
            room.getTitle(),
            room.isPlaying(),
            room.isLocked(),
            room.getPlayers().size()
        );
    }
}
