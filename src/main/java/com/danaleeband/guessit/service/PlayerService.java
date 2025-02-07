package com.danaleeband.guessit.service;

import com.danaleeband.guessit.model.dto.PlayerCreateDTO;
import com.danaleeband.guessit.model.entity.Player;
import com.danaleeband.guessit.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player createPlayer(PlayerCreateDTO playerCreateDTO) {
        Player player = new Player(playerCreateDTO.getNickname(), playerCreateDTO.getProfileUrl());
        return playerRepository.save(player);
    }
}
