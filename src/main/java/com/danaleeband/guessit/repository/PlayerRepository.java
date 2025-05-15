package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.entity.Player;
import java.util.Optional;

public interface PlayerRepository {

    Long save(Player player);

    Optional<Player> findById(Long id);
}
