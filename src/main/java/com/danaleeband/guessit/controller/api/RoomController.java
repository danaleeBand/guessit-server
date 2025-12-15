package com.danaleeband.guessit.controller.api;

import com.danaleeband.guessit.controller.api.dto.RoomCreateRequestDto;
import com.danaleeband.guessit.controller.api.dto.RoomCreateResponseDto;
import com.danaleeband.guessit.controller.api.dto.RoomJoinReponseDto;
import com.danaleeband.guessit.controller.api.dto.RoomJoinRequestDto;
import com.danaleeband.guessit.entity.Room;
import com.danaleeband.guessit.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<RoomCreateResponseDto> createRoom(
        @RequestBody @Valid RoomCreateRequestDto roomCreateRequestDto) {
        RoomCreateResponseDto roomCreateResponseDto = new RoomCreateResponseDto(
            roomService.createRoom(roomCreateRequestDto));
        return ResponseEntity.ok(roomCreateResponseDto);
    }

    @GetMapping()
    @Operation(summary = "방 전체 조회", description = "방 전체 조회")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/{roomId}/join")
    @Operation(summary = "방 입장", description = "방 입장")
    public ResponseEntity<RoomJoinReponseDto> joinRoom(@PathVariable long roomId,
        @RequestBody @Valid RoomJoinRequestDto roomJoinRequestDto) {
        return ResponseEntity.ok(roomService.joinRoom(roomId, roomJoinRequestDto));
    }
}