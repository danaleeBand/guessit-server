package com.danaleeband.guessit.game;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameSocketController {

    private final GameService gameService;

    @MessageMapping("/rooms/{roomId}/game/start")
    public void startGame(@DestinationVariable long roomId) {
        gameService.startGame(roomId);
    }
}
