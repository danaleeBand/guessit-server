package com.danaleeband.guessit.player.controller;

import com.danaleeband.guessit.player.PlayerService;
import com.danaleeband.guessit.player.dto.PlayerCreateRequestDto;
import com.danaleeband.guessit.player.dto.PlayerCreateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "플레이어")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping()
    @Operation(summary = "플레이어 생성", description = "플레이어 생성")
    public PlayerCreateResponseDto createPlayer(@RequestBody @Valid PlayerCreateRequestDto playerCreateRequestDto) {
        return playerService.createPlayer(playerCreateRequestDto);
    }
}
