package com.danaleeband.guessit.service;

import static com.danaleeband.guessit.global.Constants.ALPHABET_NUMBER;
import static com.danaleeband.guessit.global.Constants.ROOM_CODE_LENGTH;

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


    public long createRoom(RoomCreateRequestDto roomCreateRequestDTO) {
        Player creator = playerService.findPlayerById(roomCreateRequestDTO.getCreatorId());

        Room room = new Room(
            generateUniqueRoomCode(),
            roomCreateRequestDTO.getTitle(),
            roomCreateRequestDTO.isLocked(),
            roomCreateRequestDTO.getPassword(),
            creator,
            List.of(creator));

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
            char c = ALPHABET_NUMBER.charAt(random.nextInt(ALPHABET_NUMBER.length()));
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

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
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

        Player player = playerService.findPlayerById(roomJoinRequestDto.getPlayerId());
        room.addPlayer(player);
        roomRepository.updatePlayer(room);
        eventPublisher.publishEvent(new RoomListEvent("UPDATE"));

        return RoomJoinReponseDto.getValidResponse();
    }

    public void updatePlayerReady(Long roomId, Long playerId) {
        Room room = getRoomById(roomId);
        List<Player> players = room.getPlayers();

        for (Player player : players) {
            if (player.getId() == playerId) {
                player.setIsReady(!Boolean.TRUE.equals(player.getIsReady()));
                break;
            }
        }

        updateRoom(room);
    }

    public void updateRoom(Room room) {
        roomRepository.updatePlayer(room);
    }
}
