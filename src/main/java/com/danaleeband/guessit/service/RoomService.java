package com.danaleeband.guessit.service;

import com.danaleeband.guessit.domain.GAME_STATUS;
import com.danaleeband.guessit.domain.RoomConstants;
import com.danaleeband.guessit.model.dto.RoomCreateDTO;
import com.danaleeband.guessit.model.entity.Player;
import com.danaleeband.guessit.model.entity.Room;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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

    public Room createRoom(RoomCreateDTO roomCreateDTO) throws JsonProcessingException {
        String roomId = UUID.randomUUID().toString();

        GAME_STATUS status = GAME_STATUS.WAITING;

        List<Long> quizIds = quizService.getRandomQuizzes(10);

        Player player = new Player("이름");
        List<Player> players = List.of(player);

        String title = roomCreateDTO.getTitle();
        String code = generateUniqueRoomCode();
        String password = roomCreateDTO.getPassword();
        Boolean locked = roomCreateDTO.getLocked();

        Room room = new Room(roomId, code, title, status, locked, password, players, quizIds, quizIds.get(0));

        String playersJson = new ObjectMapper().writeValueAsString(players);
        String quizIdsJson = new ObjectMapper().writeValueAsString(quizIds);

        redisTemplate.opsForHash().putAll(RoomConstants.ROOM_PREFIX + roomId, Map.of(
            "id", room.getId(),
            "code", code,
            "title", roomCreateDTO.getTitle(),
            "status", room.getStatus().toString(),
            "locked", roomCreateDTO.getLocked(),
            "password", roomCreateDTO.getPassword()
        ));

        redisTemplate.opsForHash().put(RoomConstants.ROOM_PREFIX + roomId, "players", playersJson);
        redisTemplate.opsForHash().put(RoomConstants.ROOM_PREFIX + roomId, "quizIds", quizIdsJson);

        redisTemplate.opsForSet().add("room:" + roomId + ":players", new ObjectMapper().writeValueAsString(player));
        redisTemplate.opsForList().rightPushAll("room:" + roomId + ":quizzes", quizIds);

        return room;
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
