package com.danaleeband.guessit.websocket;

import com.danaleeband.guessit.service.RoomService;
import com.danaleeband.guessit.websocket.dto.RoomDetailSocketDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class RoomWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final RoomService roomService;

    private static final Logger log = LoggerFactory.getLogger(RoomWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Session Connected: {}", session);
        sessions.add(session);

        Long roomId = extractRoomId(session);
        if (roomId == null) {
            log.warn("Invalid roomId in session URI: {}", session.getUri());
            return;
        }

        sendRoom(session, toRoomSocket(roomId));
    }

    private Long extractRoomId(WebSocketSession session) {
        try {
            String path = session.getUri().getPath();
            String[] parts = path.split("/");
            return Long.parseLong(parts[parts.length - 1]);
        } catch (Exception e) {
            log.error("Failed to extract roomId from URI: {}", session.getUri(), e);
            return null;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Session Closed: {}", session);
        sessions.remove(session);
    }

    private void sendRoom(WebSocketSession session, RoomDetailSocketDto room) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(room)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RoomDetailSocketDto toRoomSocket(long id) {
        return RoomDetailSocketDto.toRoomDetailSocketDto(roomService.getRoomById(id));
    }
}
