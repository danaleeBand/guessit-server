package com.danaleeband.guessit.game;

import com.danaleeband.guessit.global.GameState;
import com.danaleeband.guessit.quiz.Quiz;
import com.danaleeband.guessit.quiz.QuizService;
import com.danaleeband.guessit.quiz.dto.HintResponseDto;
import com.danaleeband.guessit.room.Room;
import com.danaleeband.guessit.room.RoomService;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final QuizService quizService;
    private final RoomService roomService;
    private final TaskScheduler taskScheduler;
    private final SimpMessagingTemplate template;

    public Game createNewGame() {
        List<Quiz> quizList = quizService.getRandomQuizzes();
        return new Game(quizList);
    }

    public void startGame(long roomId) {
        Game game = createNewGame();

        Room room = roomService.updateRoomStart(roomId, game);

        changeGameState(room, GameState.COUNTDOWN);
        scheduleGameStartCountDown(roomId, 3);
    }

    public void scheduleGameStartCountDown(long roomId, int seconds) {
        for (int i = seconds; i >= 0; i--) {
            int count = i;

            taskScheduler.schedule(
                () -> template.convertAndSend("/sub/rooms/" + roomId + "game/countdown", count),
                Instant.now().plusSeconds(seconds - i)
            );
        }

        taskScheduler.schedule(
            () -> onCountdownFinished(roomId),
            Instant.now().plusSeconds((long) seconds + 1)
        );
    }

    public void onCountdownFinished(long roomId) {
        Room room = roomService.getRoomById(roomId);
        changeGameState(room, GameState.HINT);
        Game game = room.getGame();
        publishCurrentQuiz(room.getId(), game);
    }

    private void publishCurrentQuiz(long roomId, Game game) {
        Quiz quiz = game.currentQuiz();
        scheduleHints(roomId, quiz);
    }

    private void scheduleHints(long roomId, Quiz quiz) {
        int intervalSeconds = 5;
        List<String> hints = quiz.getHints();
        for (int i = 0; i < hints.size(); i++) {
            String hint = hints.get(i);
            HintResponseDto hintResponseDto = new HintResponseDto(i + 1, quiz.getId(), hint, quiz.getAnswer().length());
            taskScheduler.schedule(
                () -> template.convertAndSend("/sub/rooms/" + roomId + "game/hint", hintResponseDto),
                Instant.now().plusSeconds((long) intervalSeconds * i)
            );
        }

        taskScheduler.schedule(
            () -> onAllHintsFinished(roomId),
            Instant.now().plusSeconds((long) intervalSeconds * hints.size())
        );
    }

    private void onAllHintsFinished(long roomId) {
        Room room = roomService.getRoomById(roomId);
        changeGameState(room, GameState.SCORING);
    }

    private void changeGameState(Room room, GameState gameState) {
        room.getGame().changeState(gameState);
        roomService.updateRoom(room);
        publishGameState(room.getId(), gameState);
    }

    private void publishGameState(long roomId, GameState gameState) {
        template.convertAndSend("/sub/rooms/" + roomId + "/game/state", gameState.name());
    }
}
