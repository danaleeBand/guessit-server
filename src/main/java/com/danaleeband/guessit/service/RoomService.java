package com.danaleeband.guessit.service;

import static com.danaleeband.guessit.domain.RoomConstants.ROOM_INCREMENT_KEY;
import static com.danaleeband.guessit.domain.RoomConstants.ROOM_PREFIX;

import com.danaleeband.guessit.model.dto.RoomCreateDTO;
import com.danaleeband.guessit.model.entity.Player;
import com.danaleeband.guessit.model.entity.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final QuizService quizService;
    private final PlayerService playerService;

    public Room createRoom(RoomCreateDTO roomCreateDTO) {
        String roomKey = generateKey();
        List<Long> quizIds = quizService.getRandomQuizzes(10);
        Player creator = playerService.findPlayerById(roomCreateDTO.getCreatorId());

        Room room = new Room(
            roomKey,
            generateUniqueRoomCode(),
            roomCreateDTO.getTitle(),
            roomCreateDTO.getLocked(),
            roomCreateDTO.getPassword(),
            creator,
            List.of(creator),
            quizIds);

        redisTemplate.opsForValue().set(roomKey, room);

        return room;
    }

    private String generateKey() {
        Long id = redisTemplate.opsForValue().increment(ROOM_INCREMENT_KEY);
        return ROOM_PREFIX + id;
    }

    private String generateUniqueRoomCode() {
        SecureRandom random = new SecureRandom();
        String code;

        do {
            StringBuilder codeBuilder = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                int randomIndex = random.nextInt(36);  // 숫자(0-9) + 대문자(A-Z)
                if (randomIndex < 10) {
                    codeBuilder.append(randomIndex);
                } else {
                    codeBuilder.append((char) ('A' + randomIndex - 10));
                }
            }
            code = codeBuilder.toString();
        } while (redisTemplate.opsForHash().values("code").contains(code));

        return "#" + code;
    }

    public List<Room> getAllRooms() {
        Set<String> roomKeys = redisTemplate.keys(ROOM_PREFIX + "*");
        List<Room> rooms = new ArrayList<>();
        for (String roomKey : roomKeys) {
            Room room = new ObjectMapper().convertValue(redisTemplate.opsForValue().get(roomKey), Room.class);
            rooms.add(room);
        }

        return rooms;
    }

}
