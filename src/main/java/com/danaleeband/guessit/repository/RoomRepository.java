package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.entity.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository {

    Long save(Room room);

    List<Room> findAll();

    Optional<Room> findById(Long id);
}
