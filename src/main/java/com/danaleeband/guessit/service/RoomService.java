package com.danaleeband.guessit.service;

import com.danaleeband.guessit.controller.dto.RoomCreateRequestDto;
import com.danaleeband.guessit.entity.Player;
import com.danaleeband.guessit.entity.Room;
import com.danaleeband.guessit.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.SecureRandom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final QuizService quizService;
    private final PlayerService playerService;
    private final ObjectMapper objectMapper;
    private final RoomRepository roomRepository;

    public long createRoom(RoomCreateRequestDto roomCreateRequestDTO) {
        List<Long> quizIds = quizService.getRandomQuizzes(10);
        Player creator = playerService.findPlayerById(roomCreateRequestDTO.getCreatorId());

        Room room = new Room(
            generateUniqueRoomCode(),
            roomCreateRequestDTO.getTitle(),
            roomCreateRequestDTO.isLocked(),
            roomCreateRequestDTO.getPassword(),
            creator,
            List.of(creator),
            quizIds);

        return roomRepository.save(room);
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
        return roomRepository.findAll();
    }

}
