package com.danaleeband.guessit.controller.websocket;

import com.danaleeband.guessit.service.RoomService;
import com.danaleeband.guessit.websocket.dto.PlayerReadyRequestDto;
import com.danaleeband.guessit.websocket.dto.RoomDetailSocketDto;
import com.danaleeband.guessit.websocket.dto.RoomSocketDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    @SendTo("/sub/rooms")
    public String sendRoomList(String roomId) {
//        System.out.println("sending room list");
//        broadcastRoomList();
        return roomId;
    }

    private void broadcastRoomList() {
        List<RoomSocketDto> roomList = roomService.getAllOrderedRooms().stream()
            .map(RoomSocketDto::toRoomSocketDto)
            .toList();
        System.out.println("????");

        messagingTemplate.convertAndSend("/topic/rooms", roomList);
    }
}
