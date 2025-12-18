package com.danaleeband.guessit.player.dto;

import com.danaleeband.guessit.player.Player;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerDto implements Serializable {

    private long id;
    private String nickname;
    private String profileUrl;
    private boolean ready;

    public static PlayerDto from(Player player) {
        return new PlayerDto(
            player.getId(),
            player.getNickname(),
            player.getProfileUrl(),
            Boolean.TRUE.equals(player.getIsReady())
        );
    }
}
