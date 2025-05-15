package com.danaleeband.guessit.repository;

import static com.danaleeband.guessit.global.Constants.ROOM_INCREMENT_KEY;
import static com.danaleeband.guessit.global.Constants.ROOM_PREFIX;

import com.danaleeband.guessit.entity.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomRedisRepository implements RoomRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Long save(Room room) {
        Long roomId = redisTemplate.opsForValue().increment(ROOM_INCREMENT_KEY);
        room.assignId(roomId);
        redisTemplate.opsForValue().set(ROOM_PREFIX + roomId, room);

        return roomId;
    }

    @Override
    public List<Room> findAll() {
        Set<String> roomKeys = redisTemplate.keys(ROOM_PREFIX + "*");
        List<Room> rooms = new ArrayList<>();
        for (String roomKey : roomKeys) {
            Room room = objectMapper.convertValue(redisTemplate.opsForValue().get(roomKey), Room.class);
            rooms.add(room);
        }

        return rooms;
    }

    @Override
    public Room findById(Long id) {
        return objectMapper.convertValue(redisTemplate.opsForValue().get(ROOM_PREFIX + id), Room.class);
    }

}
