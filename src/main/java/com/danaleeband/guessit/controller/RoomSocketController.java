package com.danaleeband.guessit.controller;

import com.danaleeband.guessit.service.RoomService;
import com.danaleeband.guessit.websocket.dto.RoomSocketDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Tag(name = "Room WebSocket")
public class RoomSocketController {

    private final RoomService roomService;

    @MessageMapping("/rooms")
    @SendTo("/sub/rooms")
    public List<RoomSocketDto> getRoomList() {
        return roomService.getAllOrderedRooms().stream()
            .map(RoomSocketDto::toRoomSocketDto)
            .toList();
    }
}
