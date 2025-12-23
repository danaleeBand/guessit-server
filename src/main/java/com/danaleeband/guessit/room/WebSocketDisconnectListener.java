package com.danaleeband.guessit.room;

import com.danaleeband.guessit.room.dto.RoomLeaveRequestDto;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketDisconnectListener {

    private final RoomService roomService;

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor =
            StompHeaderAccessor.wrap(event.getMessage());

        Map<String, Object> attrs = accessor.getSessionAttributes();
        if (attrs == null) {
            return;
        }

        Long roomId = (Long) attrs.get("roomId");
        Long playerId = (Long) attrs.get("playerId");

        if (roomId == null || playerId == null) {
            return;
        }

        roomService.leaveRoom(
            new RoomLeaveRequestDto(roomId, playerId)
        );
    }
}
