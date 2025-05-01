package com.danaleeband.guessit.websocket.dto;

import com.danaleeband.guessit.entity.Room;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomSocketDto implements Serializable {

    private String id;
    private String code;
    private String title;
    private String status;
    private boolean locked;
    private int playerCount;

    public static RoomSocketDto toRoomSocketDto(Room room) {
        return new RoomSocketDto(
            room.getId(),
            room.getCode(),
            room.getTitle(),
            room.getStatus().name(),
            room.getLocked(),
            room.getPlayers().size()
        );
    }
}
