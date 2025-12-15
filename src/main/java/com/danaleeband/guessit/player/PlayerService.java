package com.danaleeband.guessit.player;

import com.danaleeband.guessit.player.dto.PlayerCreateRequestDto;
import com.danaleeband.guessit.player.dto.PlayerCreateResponseDto;
import com.danaleeband.guessit.player.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerCreateResponseDto createPlayer(PlayerCreateRequestDto playerCreateRequestDto) {
        Player player = new Player(playerCreateRequestDto.getNickname(), playerCreateRequestDto.getProfileUrl());
        Long playerId = playerRepository.save(player);

        return new PlayerCreateResponseDto(playerId);
    }

    public Player findPlayerById(Long id) {
        return playerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
