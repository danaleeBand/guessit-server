package com.danaleeband.guessit.room.controller;

import com.danaleeband.guessit.room.RoomService;
import com.danaleeband.guessit.room.dto.RoomLeaveRequestDto;
import com.danaleeband.guessit.room.dto.RoomLeaveResponseDto;
import com.danaleeband.guessit.room.dto.RoomSessionBindRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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

    @MessageMapping("/rooms/join")
    public void bindSession(
        @Payload RoomSessionBindRequestDto dto,
        SimpMessageHeaderAccessor accessor
    ) {
        var sessionAttrs = accessor.getSessionAttributes();
        if (sessionAttrs != null) {
            sessionAttrs.put("roomId", dto.getRoomId());
            sessionAttrs.put("playerId", dto.getPlayerId());
        }
    }


    @MessageMapping("/rooms/leave")
    public RoomLeaveResponseDto leaveRoom(@Payload RoomLeaveRequestDto requestDto) {
        return roomService.leaveRoom(requestDto);

    @MessageMapping("/rooms/ready")
    public void ready(@Payload GameReadyRequestDto gameReadyRequestDto) {
        roomService.updatePlayerReady(gameReadyRequestDto);
    }
}