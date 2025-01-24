package com.danaleeband.guessit.service;

import static com.danaleeband.guessit.domain.RoomConstants.ROOM_INCREMENT_KEY;
import static com.danaleeband.guessit.domain.RoomConstants.ROOM_PREFIX;

import com.danaleeband.guessit.model.dto.RoomCreateDTO;
import com.danaleeband.guessit.model.entity.Player;
import com.danaleeband.guessit.model.entity.Room;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final QuizService quizService;

    @Autowired
    public RoomService(RedisTemplate<String, Object> redisTemplate, QuizService quizService) {
        this.redisTemplate = redisTemplate;
        this.quizService = quizService;
    }

    public Room createRoom(RoomCreateDTO roomCreateDTO) {
        String roomKey = generateKey();
        List<Long> quizIds = quizService.getRandomQuizzes(10);

        Room room = new Room(
            roomKey,
            generateUniqueRoomCode(),
            roomCreateDTO.getTitle(),
            roomCreateDTO.getLocked(),
            roomCreateDTO.getPassword(),
            List.of(new Player("개설자id", "개설자닉네임")),
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

    public Set<String> getAllRooms() throws JsonProcessingException {
        System.out.println("접근은 하니?");
        List<Room> rooms = new ArrayList<>();

        Set<String> roomKeys = redisTemplate.keys("room:*");
        return roomKeys;
//        System.out.println(roomKeys);
//
//        if (roomKeys != null) {
//            for (String roomKey : roomKeys) {
//                System.out.println(roomKey);
//
//                Map<Object, Object> roomData = redisTemplate.opsForHash().entries(roomKey);
//                System.out.println(roomData);
//
//                String id = (String) roomData.get("id");
//                String code = (String) roomData.get("code");
//                String title = (String) roomData.get("title");
//                GAME_STATUS status = GAME_STATUS.valueOf((String) roomData.get("status"));
//                Boolean locked = (Boolean) roomData.get("locked");
//                String password = (String) roomData.get("password");
//
//                String playersJson = (String) roomData.get("players");
//                List<Player> players = playersJson != null ?
//                    new ObjectMapper().readValue(playersJson, new TypeReference<List<Player>>() {
//                    }) : new ArrayList<>();
//
//                List<Object> quizIdsList = redisTemplate.opsForList().range("room:" + id + ":quizzes", 0, -1);
//                List<Long> quizIds = new ArrayList<>();
//                if (quizIdsList != null && !quizIdsList.isEmpty()) {
//                    for (Object quizId : quizIdsList) {
//                        quizIds.add(Long.parseLong(quizId.toString()));
//                    }
//                }
//
//                Long firstQuizId = !quizIds.isEmpty() ? quizIds.get(0) : null;
//
//                Room room = new Room(id, code, title, status, locked, password, players, quizIds, firstQuizId);
//                rooms.add(room);
//            }
//        }
//
//        return rooms;
    }

}
