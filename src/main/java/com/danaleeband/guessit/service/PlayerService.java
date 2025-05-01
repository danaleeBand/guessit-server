package com.danaleeband.guessit.service;

import com.danaleeband.guessit.controller.dto.PlayerCreateRequestDto;
import com.danaleeband.guessit.controller.dto.PlayerCreateResponseDto;
import com.danaleeband.guessit.entity.Player;
import com.danaleeband.guessit.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return playerRepository.findById(id);
    }
}
