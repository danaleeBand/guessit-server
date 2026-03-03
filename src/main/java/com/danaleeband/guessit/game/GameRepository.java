package com.danaleeband.guessit.game;

import com.danaleeband.guessit.room.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository {

    Long save(Room room);

    List<Room> findAll();

    Optional<Room> findById(Long id);

    void update(Room room);

    void delete(Long id);
}
