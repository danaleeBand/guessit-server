package com.danaleeband.guessit.service;

import com.danaleeband.guessit.controller.dto.RoomCreateRequestDto;
import com.danaleeband.guessit.controller.dto.RoomJoinReponseDto;
import com.danaleeband.guessit.controller.dto.RoomJoinRequestDto;
import com.danaleeband.guessit.entity.Player;
import com.danaleeband.guessit.entity.Room;
import com.danaleeband.guessit.global.RoomListEvent;
import com.danaleeband.guessit.repository.RoomRepository;
import jakarta.validation.Valid;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final QuizService quizService;
    private final PlayerService playerService;
    private final RoomRepository roomRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ROOM_CODE_LENGTH = 6;

    public long createRoom(RoomCreateRequestDto roomCreateRequestDTO) {
        List<Long> quizIds = quizService.getRandomQuizzes();
        Player creator = playerService.findPlayerById(roomCreateRequestDTO.getCreatorId());

        Room room = new Room(
            generateUniqueRoomCode(),
            roomCreateRequestDTO.getTitle(),
            roomCreateRequestDTO.isLocked(),
            roomCreateRequestDTO.getPassword(),
            creator,
            List.of(creator),
            quizIds);

        Long id = roomRepository.save(room);
        eventPublisher.publishEvent(new RoomListEvent("UPDATE"));

        return id;
    }

    private String generateUniqueRoomCode() {
        Set<String> roomCodeSet = getAllRooms().stream()
            .map(Room::getCode)
            .collect(Collectors.toSet());

        String code;
        do {
            code = generateRandomCode();
        } while (roomCodeSet.contains(code));

        return code;
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(ROOM_CODE_LENGTH);

        for (int i = 0; i < ROOM_CODE_LENGTH; i++) {
            char c = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            sb.append(c);
        }

        return "#" + sb;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAllOrderedRooms() {
        return roomRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(Room::isPlaying)
                .thenComparing(Room::getId, Comparator.reverseOrder()))
            .toList();
    }

    public RoomJoinReponseDto joinRoom(long roomId, @Valid RoomJoinRequestDto roomJoinRequestDto) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        if (room.isLocked() && !room.getPassword().equals(roomJoinRequestDto.getPassword())) {
            return RoomJoinReponseDto.getInvalidPasswordResponse();
        }

        if (room.isPlaying()) {
            return RoomJoinReponseDto.getFullRoomResponse();
        }

        // 방 입장

        return RoomJoinReponseDto.getValidResponse();
    }
}
