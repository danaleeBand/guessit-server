package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.entity.Player;

public interface PlayerRepository {

    Long save(Player player);

    Player findById(Long id);
}
