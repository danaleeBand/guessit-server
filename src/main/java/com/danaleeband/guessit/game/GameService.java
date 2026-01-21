package com.danaleeband.guessit.game;

import com.danaleeband.guessit.quiz.Quiz;
import com.danaleeband.guessit.quiz.QuizService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final QuizService quizService;

    public Game createNewGame() {
        List<Quiz> quizList = quizService.getRandomQuizzes();
        return new Game(quizList);
    }

}
