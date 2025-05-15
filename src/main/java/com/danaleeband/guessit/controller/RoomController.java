package com.danaleeband.guessit.controller;

import com.danaleeband.guessit.controller.dto.RoomCreateRequestDto;
import com.danaleeband.guessit.controller.dto.RoomCreateResponseDto;
import com.danaleeband.guessit.entity.Room;
import com.danaleeband.guessit.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
@Tag(name = "방")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping()
    @Operation(summary = "방 생성", description = "방 생성")
    public RoomCreateResponseDto createRoom(@RequestBody @Valid RoomCreateRequestDto roomCreateRequestDto) {
        return new RoomCreateResponseDto(roomService.createRoom(roomCreateRequestDto));
    }

    @GetMapping()
    @Operation(summary = "방 전체 조회", description = "방 전체 조회")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
}