package com.danaleeband.guessit.room.controller;

import com.danaleeband.guessit.room.RoomService;
import com.danaleeband.guessit.room.dto.RoomCreateRequestDto;
import com.danaleeband.guessit.room.dto.RoomCreateResponseDto;
import com.danaleeband.guessit.room.dto.RoomDetailDto;
import com.danaleeband.guessit.room.dto.RoomDto;
import com.danaleeband.guessit.room.dto.RoomJoinReponseDto;
import com.danaleeband.guessit.room.dto.RoomJoinRequestDto;
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
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<RoomDto> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "방 상세 조회", description = "방 상세 조회")
    public ResponseEntity<RoomDetailDto> getRoom(@PathVariable Long roomId) {
        RoomDetailDto room = roomService.getRoomDetail(roomId);
        return ResponseEntity.ok(room);
    }

    @PostMapping("/{roomId}/join")
    @Operation(summary = "방 입장", description = "방 입장")
    public ResponseEntity<RoomJoinReponseDto> joinRoom(@PathVariable long roomId,
        @RequestBody @Valid RoomJoinRequestDto roomJoinRequestDto) {
        return ResponseEntity.ok(roomService.joinRoom(roomId, roomJoinRequestDto));
    }
}