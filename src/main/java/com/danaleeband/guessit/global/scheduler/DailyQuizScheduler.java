package com.danaleeband.guessit.global.scheduler;

import com.danaleeband.guessit.quiz.QuizService;
import com.danaleeband.guessit.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyQuizScheduler {

    private final QuizService quizService;
    private final RoomService roomService;

    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    public void syncDailyQuizData() {
        var quizzes = quizService.getLatestQuizzes();
        log.info("[DailyQuiz] quiz : {}개", quizzes.size());

        var rooms = roomService.getAllRooms();
        log.info("[DailyQuiz] room : {}개", rooms.size());
    }
}
