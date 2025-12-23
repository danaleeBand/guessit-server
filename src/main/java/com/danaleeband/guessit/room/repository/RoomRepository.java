package com.danaleeband.guessit.room.repository;

import com.danaleeband.guessit.room.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository {

    Long save(Room room);

    List<Room> findAll();

    Optional<Room> findById(Long id);

    void updatePlayer(Room room);

    void delete(Long id);
}
