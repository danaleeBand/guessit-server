package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.model.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerRedisRepository implements PlayerRepository {

    private static final String KEY_PREFIX = "player:";
    private static final String INCREMENT_KEY = "player.increment"; // 자동 증가 ID 관리

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Player save(Player player) {
        String playerKey = KEY_PREFIX + redisTemplate.opsForValue().increment(INCREMENT_KEY);
        player.assignId(playerKey);
        redisTemplate.opsForValue().set(playerKey, player);

        return player;
    }
}
