package com.danaleeband.guessit.controller.websocket;

import com.danaleeband.guessit.service.RoomService;
import com.danaleeband.guessit.websocket.dto.PlayerReadyRequestDto;
import com.danaleeband.guessit.websocket.dto.RoomDetailSocketDto;
import com.danaleeband.guessit.websocket.dto.RoomSocketDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RoomSocketController {

    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/rooms/ready")
    public void handlePlayerReady(PlayerReadyRequestDto request) {
        roomService.updatePlayerReady(request.getRoomId(), request.getPlayerId());

        RoomDetailSocketDto updatedRoom = RoomDetailSocketDto.toRoomDetailSocketDto(
            roomService.getRoomById(request.getRoomId())
        );

        messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), updatedRoom);

        broadcastRoomList();
    }

    @MessageMapping("/rooms")
    public void sendRoomList() {
        broadcastRoomList();
    }

    private void broadcastRoomList() {
        List<RoomSocketDto> roomList = roomService.getAllOrderedRooms().stream()
            .map(RoomSocketDto::toRoomSocketDto)
            .toList();

        messagingTemplate.convertAndSend("/topic/rooms", roomList);
    }
}
