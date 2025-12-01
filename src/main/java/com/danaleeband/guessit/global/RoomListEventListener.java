package com.danaleeband.guessit.global;

import com.danaleeband.guessit.service.RoomService;
import com.danaleeband.guessit.websocket.dto.RoomSocketDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomListEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomService roomService;

    @EventListener
    public void handleRoomListEvent(RoomListEvent event) {
        //log.info("Room list changed: {}", event.getAction());

        List<RoomSocketDto> roomList = roomService.getAllOrderedRooms().stream()
            .map(RoomSocketDto::toRoomSocketDto)
            .toList();

        messagingTemplate.convertAndSend("/topic/rooms", roomList);
        //log.info("Broadcasted {} rooms to /topic/rooms", roomList.size());
    }
}
