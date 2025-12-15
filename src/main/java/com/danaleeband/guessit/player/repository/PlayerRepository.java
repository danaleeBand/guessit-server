package com.danaleeband.guessit.player.repository;

import com.danaleeband.guessit.player.Player;
import java.util.Optional;

public interface PlayerRepository {

    Long save(Player player);

    Optional<Player> findById(Long id);
}
