package com.danaleeband.guessit.controller;

import com.danaleeband.guessit.controller.dto.RoomCreateRequestDto;
import com.danaleeband.guessit.controller.dto.RoomCreateResponseDto;
import com.danaleeband.guessit.controller.dto.RoomPasswordReponseDto;
import com.danaleeband.guessit.controller.dto.RoomPasswordRequestDto;
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

    @PostMapping("/{roomId}/check-password")
    @Operation(summary = "방 비밀번호 체크", description = "방 비밀번호 체크")
    public ResponseEntity<RoomPasswordReponseDto> checkRoomPassword(@PathVariable long roomId,
        @RequestBody @Valid RoomPasswordRequestDto roomPasswordRequestDto) {
        RoomPasswordReponseDto response = roomService.checkRoomPassword(roomId, roomPasswordRequestDto) ?
            RoomPasswordReponseDto.getValidResponse() : RoomPasswordReponseDto.getInvalidResponse();
        return ResponseEntity.ok(response);
    }
}