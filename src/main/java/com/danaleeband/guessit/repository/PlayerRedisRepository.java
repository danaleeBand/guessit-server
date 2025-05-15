package com.danaleeband.guessit.repository;

import static com.danaleeband.guessit.global.Constants.PLAYER_INCREMENT_KEY;
import static com.danaleeband.guessit.global.Constants.PLAYER_PREFIX;

import com.danaleeband.guessit.entity.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerRedisRepository implements PlayerRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Long save(Player player) {
        Long playerId = redisTemplate.opsForValue().increment(PLAYER_INCREMENT_KEY);
        player.assignId(playerId);
        redisTemplate.opsForValue().set(PLAYER_PREFIX + playerId, player);

        return playerId;
    }

    @Override
    public Optional<Player> findById(Long id) {
        String playerKey = PLAYER_PREFIX + id;
        return Optional.ofNullable(objectMapper.convertValue(redisTemplate.opsForValue().get(playerKey), Player.class));
    }
}
