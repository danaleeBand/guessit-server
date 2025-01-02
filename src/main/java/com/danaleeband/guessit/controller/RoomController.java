package com.danaleeband.guessit.controller;

import com.danaleeband.guessit.model.dto.RoomCreateDTO;
import com.danaleeband.guessit.model.entity.Room;
import com.danaleeband.guessit.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@Tag(name = "방")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping()
    @Operation(summary = "방 생성", description = "방 생성")
    public Room createRoom(@RequestBody @Valid RoomCreateDTO roomCreateDTO) throws JsonProcessingException {
        return roomService.createRoom(roomCreateDTO);
    }

    @GetMapping()
    @Operation(summary = "방 전체 조회", description = "방 전체 조회")
    public ResponseEntity<Set<String>> getAllRooms() throws JsonProcessingException {
        Set<String> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
}