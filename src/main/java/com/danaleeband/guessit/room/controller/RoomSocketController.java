package com.danaleeband.guessit.room.controller;

import com.danaleeband.guessit.room.RoomService;
import com.danaleeband.guessit.room.dto.GameReadyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RoomSocketController {

    private final RoomService roomService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/rooms/ready")
    public void ready(@Payload GameReadyRequestDto gameReadyRequestDto) {
        roomService.updatePlayerReady(gameReadyRequestDto);
    }
}