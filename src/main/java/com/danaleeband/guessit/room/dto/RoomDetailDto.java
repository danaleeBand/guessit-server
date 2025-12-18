package com.danaleeband.guessit.room.dto;

import com.danaleeband.guessit.player.dto.PlayerDto;
import com.danaleeband.guessit.room.Room;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomDetailDto implements Serializable {

    private long id;
    private String code;
    private String title;
    private boolean playing;
    private boolean locked;
    private PlayerDto creator;
    private List<PlayerDto> players;

    public static RoomDetailDto toRoomDetailDto(Room room) {
        return new RoomDetailDto(
            room.getId(),
            room.getCode(),
            room.getTitle(),
            room.isPlaying(),
            room.isLocked(),
            PlayerDto.from(room.getCreator()),
            room.getPlayers()
                .stream()
                .map(PlayerDto::from)
                .toList()
        );
    }
}
