package com.danaleeband.guessit.service;

import com.danaleeband.guessit.domain.GAME_STATUS;
import com.danaleeband.guessit.domain.RoomConstants;
import com.danaleeband.guessit.model.dto.RoomCreateDTO;
import com.danaleeband.guessit.model.entity.Player;
import com.danaleeband.guessit.model.entity.Room;
import java.util.List;
import java.util.Map;
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

    public Room createRoom(RoomCreateDTO roomCreateDTO) {
        String roomId = UUID.randomUUID().toString();

        GAME_STATUS status = GAME_STATUS.WAITING;

        List<Long> quizIds = quizService.getRandomQuizzes(10);

        Player player = new Player("이름");
        List<Player> players = List.of(player);

        String title = roomCreateDTO.getTitle();

        String password = roomCreateDTO.getPassword();

        Boolean locked = roomCreateDTO.getLocked();

        Room room = new Room(roomId, title, status, locked, password, players, quizIds, quizIds.get(0));
        redisTemplate.opsForHash().putAll(RoomConstants.ROOM_PREFIX + roomId, Map.of(
            "id", room.getId(),
            "title", roomCreateDTO.getTitle(),
            "status", room.getStatus().toString(),
            "rocked", roomCreateDTO.getLocked(),
            "password", roomCreateDTO.getPassword(),
            "players", players,
            "quizIds", quizIds
        ));

        redisTemplate.opsForSet().add("room:" + roomId + ":players", player);
        redisTemplate.opsForList().rightPushAll("room:" + roomId + ":quizzes", quizIds);

        return room;
    }
}
