package com.danaleeband.guessit.game;

import com.danaleeband.guessit.game.dto.SubmitAnswerRequestDto;
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

    @MessageMapping("/rooms/{roomId}/submit")
    public void submitAnswer(@DestinationVariable long roomId, SubmitAnswerRequestDto request) {
        gameService.submitAnswer(roomId, request);
    }
}
