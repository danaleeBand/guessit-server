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
public class KeepAliveScheduler {

    private final QuizService quizService;
    private final RoomService roomService;

    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    public void keepAlive() {
        var quizzes = quizService.getLatestQuizzes();
        log.info("[KeepAlive] quiz 조회 완료: {}개", quizzes.size());

        var rooms = roomService.getAllRooms();
        log.info("[KeepAlive] room 조회 완료: {}개", rooms.size());
    }
}
