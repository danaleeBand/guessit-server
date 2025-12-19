package com.danaleeband.guessit.room;

import static com.danaleeband.guessit.global.Constants.ALPHABET_NUMBER;
import static com.danaleeband.guessit.global.Constants.ROOM_CODE_LENGTH;

import com.danaleeband.guessit.player.Player;
import com.danaleeband.guessit.player.PlayerService;
import com.danaleeband.guessit.quiz.QuizService;
import com.danaleeband.guessit.room.dto.RoomCreateRequestDto;
import com.danaleeband.guessit.room.dto.RoomDetailDto;
import com.danaleeband.guessit.room.dto.RoomDto;
import com.danaleeband.guessit.room.dto.RoomJoinRequestDto;
import com.danaleeband.guessit.room.dto.RoomJoinResponseDto;
import com.danaleeband.guessit.room.repository.RoomRepository;
import jakarta.validation.Valid;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final QuizService quizService;
    private final PlayerService playerService;
    private final RoomRepository roomRepository;
    private final SimpMessagingTemplate template;


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

        broadcastRoomList();
        broadcastRoomDetail(id);

        return id;
    }

    private String generateUniqueRoomCode() {
        Set<String> roomCodeSet = roomRepository.findAll().stream()
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

    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(Room::isPlaying)
                .thenComparing(Room::getId, Comparator.reverseOrder()))
            .map(RoomDto::toRoomDto)
            .toList();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    public RoomDetailDto getRoomDetail(Long id) {
        return RoomDetailDto.toRoomDetailDto(
            roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id))
        );
    }

    public RoomJoinResponseDto joinRoom(long roomId, @Valid RoomJoinRequestDto roomJoinRequestDto) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (room.isLocked() && !room.getPassword().equals(roomJoinRequestDto.getPassword())) {
            return RoomJoinResponseDto.getInvalidPasswordResponse();
        }

        if (room.isPlaying()) {
            return RoomJoinResponseDto.getFullRoomResponse();
        }

        if (isDuplicated(room, roomJoinRequestDto.getPlayerId())) {
            return RoomJoinResponseDto.getPlayerDuplicatedResponse();
        }

        Player player = playerService.findPlayerById(roomJoinRequestDto.getPlayerId());
        room.addPlayer(player);
        roomRepository.updatePlayer(room);

        broadcastRoomList();
        broadcastRoomDetail(roomId);

        return RoomJoinResponseDto.getValidResponse();
    }

    private static boolean isDuplicated(Room room, long playerId) {
        return room.getPlayers()
            .stream()
            .map(Player::getId)
            .toList()
            .contains(playerId);
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

        broadcastRoomDetail(roomId);
    }

    public void updateRoom(Room room) {
        roomRepository.updatePlayer(room);
    }

    public void broadcastRoomList() {
        template.convertAndSend("/sub/rooms", getAllRooms());
    }

    public void broadcastRoomDetail(Long roomId) {
        RoomDetailDto detail = getRoomDetail(roomId);
        template.convertAndSend("/sub/rooms/" + roomId, detail);
    }
}
