package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.entity.Room;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository {

    Long save(Room room);

    List<Room> findAll();

    Room findById(Long id);
}
