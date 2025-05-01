package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.entity.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerRedisRepository implements PlayerRepository {

    private static final String KEY_PREFIX = "player:";
    private static final String INCREMENT_KEY = "player.increment";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long save(Player player) {
        Long playerId = redisTemplate.opsForValue().increment(INCREMENT_KEY);
        player.assignId(playerId);
        redisTemplate.opsForValue().set(KEY_PREFIX + playerId, player);

        return playerId;
    }

    @Override
    public Player findById(Long id) {
        String playerKey = KEY_PREFIX + id;
        return new ObjectMapper().convertValue(redisTemplate.opsForValue().get(playerKey), Player.class);
    }
}
