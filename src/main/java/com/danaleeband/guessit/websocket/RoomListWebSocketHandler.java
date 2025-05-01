package com.danaleeband.guessit.websocket;

import com.danaleeband.guessit.entity.Room;
import com.danaleeband.guessit.websocket.dto.RoomSocketDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
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
public class RoomListWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    private static final Logger log = LoggerFactory.getLogger(RoomListWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Session Connected: {}", session);
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Session Closed: {}", session);
        sessions.remove(session);
    }

    public void broadcastRoomList(List<Room> roomList) {
        for (WebSocketSession session : sessions) {
            sendRoomList(session, toRoomSocketList(roomList));
        }
    }

    private void sendRoomList(WebSocketSession session, List<RoomSocketDto> roomList) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(roomList)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<RoomSocketDto> toRoomSocketList(List<Room> roomList) {
        return roomList.stream()
            .map(RoomSocketDto::toRoomSocketDto)
            .toList();
    }
}
