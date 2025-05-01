package com.danaleeband.guessit.global;

import com.danaleeband.guessit.websocket.RoomListWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomListEventListener {

    private static final Logger log = LoggerFactory.getLogger(RoomListEventListener.class);
    private final RoomListWebSocketHandler roomListWebSocketHandler;

    @EventListener
    public void handleRoomListEvent(RoomListEvent event) {
        RoomListEventListener.log.info("✅ 이벤트 수신: {} {}", event.getClass(), event.getMessage());
        roomListWebSocketHandler.broadcastRoomList();
    }
}
