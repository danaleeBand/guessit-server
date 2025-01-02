package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.model.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {

}
