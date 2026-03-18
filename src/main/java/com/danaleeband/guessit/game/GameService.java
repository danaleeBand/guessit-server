package com.danaleeband.guessit.game;

import com.danaleeband.guessit.game.dto.ScoreResponseDto;
import com.danaleeband.guessit.game.dto.SubmitAnswerRequestDto;
import com.danaleeband.guessit.game.dto.SubmitAnswerResponseDto;
import com.danaleeband.guessit.global.GameState;
import com.danaleeband.guessit.player.Player;
import com.danaleeband.guessit.quiz.Quiz;
import com.danaleeband.guessit.quiz.QuizService;
import com.danaleeband.guessit.quiz.dto.HintResponseDto;
import com.danaleeband.guessit.quiz.dto.PlayerResultDto;
import com.danaleeband.guessit.quiz.dto.QuizResultDto;
import com.danaleeband.guessit.room.Room;
import com.danaleeband.guessit.room.RoomService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
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

    private static final String ROOM_TOPIC_PREFIX = "/sub/rooms/";

    public Game createNewGame() {
        List<Quiz> quizList = quizService.getRandomQuizzes();
        return new Game(quizList);
    }

    public void startGame(long roomId) {
        Game game = createNewGame();

        Room room = roomService.getRoomById(roomId);

        for (Player player : room.getPlayers()) {
            game.initPlayerScore(player.getId());
        }

        room.setGame(game);
        roomService.updateRoomStart(roomId, game);

        changeGameState(room, GameState.COUNTDOWN);
        scheduleGameStartCountDown(roomId, 3);
    }

    public void scheduleGameStartCountDown(long roomId, int seconds) {
        for (int i = seconds; i >= 0; i--) {
            int count = i;

            taskScheduler.schedule(
                () -> template.convertAndSend(ROOM_TOPIC_PREFIX + roomId + "/game/countdown", count),
                Instant.now().plusSeconds(seconds - (long) i)
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

    private final Map<Long, List<ScheduledFuture<?>>> scheduledHintFutures = new ConcurrentHashMap<>();

    private void scheduleHints(long roomId, Quiz quiz) {
        int intervalSeconds = 5;
        List<String> hints = quiz.getHints();
        List<ScheduledFuture<?>> futures = new ArrayList<>();

        for (int i = 0; i < hints.size(); i++) {
            String hint = hints.get(i);
            HintResponseDto hintResponseDto = new HintResponseDto(i + 1, quiz.getId(), hint, quiz.getAnswer().length());

            ScheduledFuture<?> future = taskScheduler.schedule(
                () -> {
                    Room room = roomService.getRoomById(roomId);
                    Game game = room.getGame();

                    if (game.getGameState() != GameState.HINT) {
                        return;
                    }

                    template.convertAndSend(
                        ROOM_TOPIC_PREFIX + roomId + "/game/hint",
                        hintResponseDto
                    );

                    game.increaseRevealedHintCount();
                    roomService.updateRoom(room);
                },
                Instant.now().plusSeconds((long) intervalSeconds * i)
            );

            futures.add(future);
        }

        ScheduledFuture<?> endFuture = taskScheduler.schedule(
            () -> onAllHintsFinished(roomId),
            Instant.now().plusSeconds((long) intervalSeconds * hints.size())
        );
        futures.add(endFuture);

        scheduledHintFutures.put(roomId, futures);
    }

    private void onAllHintsFinished(long roomId) {
        Room room = roomService.getRoomById(roomId);
        Game game = room.getGame();

        if (game.getGameState() != GameState.HINT) {
            return;
        }

        changeGameState(room, GameState.SCORING);
        processRoundScoring(room);
    }

    private void changeGameState(Room room, GameState gameState) {
        room.getGame().changeState(gameState);
        roomService.updateRoom(room);
        publishGameState(room.getId(), gameState);
    }

    private void publishGameState(long roomId, GameState gameState) {
        template.convertAndSend(ROOM_TOPIC_PREFIX + roomId + "/game/state", gameState.name());
    }

    public void submitAnswer(long roomId, SubmitAnswerRequestDto request) {
        Room room = roomService.getRoomById(roomId);
        Game game = room.getGame();

        if (game.hasSubmitted(request.getPlayerId(), request.getQuizId())) {
            return;
        }

        Quiz currentQuiz = game.currentQuiz();
        if (currentQuiz.getId() != request.getQuizId()) {
            return;
        }

        boolean isCorrect = currentQuiz.isCorrect(request.getAnswer());

        AnswerSubmission submission = new AnswerSubmission(
            request.getPlayerId(),
            request.getQuizId(),
            request.getAnswer(),
            isCorrect
        );

        game.submitAnswer(submission);
        roomService.updateRoom(room);

        List<AnswerSubmission> sorted =
            game.getSubmissionsForCurrentQuiz(request.getQuizId()).stream()
                .sorted(Comparator.comparing(AnswerSubmission::getSubmittedAt))
                .toList();

        List<SubmitAnswerResponseDto> response = new ArrayList<>();

        for (int i = 0; i < sorted.size(); i++) {
            AnswerSubmission s = sorted.get(i);
            response.add(
                new SubmitAnswerResponseDto(
                    s.getPlayerId(),
                    s.getQuizId(),
                    i + 1L
                )
            );
        }

        template.convertAndSend(
            ROOM_TOPIC_PREFIX + roomId + "/game/submissions",
            response
        );

        if (game.getGameState() == GameState.HINT
            && allPlayersSubmitted(room, game)) {
            revealAllHints(room);
            taskScheduler.schedule(
                () -> onAllHintsFinished(roomId),
                Instant.now().plusSeconds(3)
            );
        }
    }

    private boolean allPlayersSubmitted(Room room, Game game) {
        long quizId = game.currentQuiz().getId();

        int totalPlayers = room.getPlayers().size();
        int submittedCount = game.getSubmissionsForCurrentQuiz(quizId).size();

        return submittedCount >= totalPlayers;
    }

    private void revealAllHints(Room room) {
        long roomId = room.getId();

        Room freshRoom = roomService.getRoomById(roomId);
        Game game = freshRoom.getGame();
        Quiz quiz = game.currentQuiz();
        List<String> hints = quiz.getHints();

        List<ScheduledFuture<?>> futures = scheduledHintFutures.remove(roomId);
        if (futures != null) {
            futures.forEach(f -> f.cancel(false));
        }

        int alreadyRevealed = game.getRevealedHintCount();

        for (int i = alreadyRevealed; i < hints.size(); i++) {
            template.convertAndSend(
                ROOM_TOPIC_PREFIX + roomId + "/game/hint",
                new HintResponseDto(
                    i + 1,
                    quiz.getId(),
                    hints.get(i),
                    quiz.getAnswer().length()
                )
            );
        }

        game.setRevealedHintCount(hints.size());
        roomService.updateRoom(room);
    }

    private void processRoundScoring(Room room) {
        Game game = room.getGame();
        Quiz quiz = game.currentQuiz();

        List<AnswerSubmission> submissions =
            game.getSubmissionsForCurrentQuiz(quiz.getId());

        // 정답만 필터 + 제출시간순 정렬
        List<AnswerSubmission> correctSubmissions =
            submissions.stream()
                .filter(AnswerSubmission::isCorrect)
                .sorted(Comparator.comparing(AnswerSubmission::getSubmittedAt))
                .toList();

        // 라운드 점수 계산용 Map
        Map<Long, Integer> roundScoreMap = new HashMap<>();

        for (int i = 0; i < correctSubmissions.size(); i++) {
            AnswerSubmission s = correctSubmissions.get(i);
            int rank = i + 1;
            int score = calculateScore(rank);

            roundScoreMap.put(s.getPlayerId(), score);
            game.addScore(s.getPlayerId(), score);
        }

        // 전체 제출 기준으로 결과 DTO 생성
        List<PlayerResultDto> results = submissions.stream()
            .sorted(Comparator.comparing(AnswerSubmission::getSubmittedAt))
            .map(s -> new PlayerResultDto(
                s.getPlayerId(),
                s.isCorrect(),
                s.getAnswer(),
                calculateRank(s, correctSubmissions),
                roundScoreMap.getOrDefault(s.getPlayerId(), 0)
            ))
            .toList();

        QuizResultDto response = new QuizResultDto(
            game.getCurrentQuizIndex() + 1,
            quiz.getId(),
            results,
            quiz.getAnswer()
        );

        template.convertAndSend(
            ROOM_TOPIC_PREFIX + room.getId() + "/game/answer",
            response
        );

        recalculateRanks(game);
        roomService.updateRoom(room);
        publishTotalScores(room);

        taskScheduler.schedule(
            () -> scheduleNextRoundOrEnd(room.getId()),
            Instant.now().plusSeconds(5)
        );
    }

    private void recalculateRanks(Game game) {
        List<PlayerScore> sorted =
            game.getPlayerScores().values().stream()
                .sorted((a, b) -> b.getScore() - a.getScore())
                .toList();

        for (int i = 0; i < sorted.size(); i++) {
            if (i > 0 && sorted.get(i).getScore() == sorted.get(i - 1).getScore()) {
                sorted.get(i).setRank(sorted.get(i - 1).getRank());
            } else {
                sorted.get(i).setRank(i + 1);
            }
        }
    }

    private int calculateScore(int rank) {
        if (rank == 1) {
            return 5;
        }
        if (rank == 2) {
            return 3;
        }
        return 1;
    }

    private int calculateRank(AnswerSubmission submission,
        List<AnswerSubmission> correctSubmissions) {

        for (int i = 0; i < correctSubmissions.size(); i++) {
            if (correctSubmissions.get(i).getPlayerId()
                == submission.getPlayerId()) {
                return i + 1;
            }
        }

        return 0;
    }

    private void scheduleNextRoundOrEnd(long roomId) {
        Room room = roomService.getRoomById(roomId);
        Game game = room.getGame();

        if (game.hasNextQuiz()) {
            game.moveToNextQuiz();
            game.resetRevealedHintCount();
            changeGameState(room, GameState.COUNTDOWN);
            scheduleGameStartCountDown(roomId, 3);
        } else {
            changeGameState(room, GameState.FINISHED);
            room.setGame(null);
            roomService.updateRoom(room);
            roomService.updateRoomEnd(roomId);
        }
    }

    private void publishTotalScores(Room room) {
        Game game = room.getGame();

        List<ScoreResponseDto> scores =
            game.getPlayerScores().values().stream()
                .sorted(Comparator.comparingInt(PlayerScore::getRank))
                .map(ps -> new ScoreResponseDto(
                    ps.getPlayerId(),
                    ps.getScore(),
                    ps.getRank()
                ))
                .toList();

        template.convertAndSend(
            ROOM_TOPIC_PREFIX + room.getId() + "/game/scores",
            scores
        );
    }
}
