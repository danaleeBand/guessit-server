package com.danaleeband.guessit;

import com.danaleeband.guessit.domain.GAME_STATUS;
import com.danaleeband.guessit.model.entity.Room;
import com.danaleeband.guessit.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RoomRedisTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void saveRoomTest() {
        Room room = new Room(1, GAME_STATUS.PLAYING, 5);
        roomRepository.save(room);
    }

    @Test
    void getRoomTest() {
        Room room = roomRepository.findById(1).orElseThrow(() -> new RuntimeException("Room not found"));
        System.out.println(room.getId() + ", " + room.getQuizId());
    }
}
