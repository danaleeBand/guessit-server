package com.danaleeband.guessit.game;

import com.danaleeband.guessit.global.GameState;
import com.danaleeband.guessit.quiz.Quiz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("game")
public class Game implements Serializable {

    private List<Quiz> quizList;
    private GameState gameState;
    private int currentQuizIndex = 0;

    public Game() {
    }

    public Quiz currentQuiz() {
        return quizList.get(currentQuizIndex);
    }

    public boolean hasNextQuiz() {
        return currentQuizIndex + 1 < quizList.size();
    }

    public void moveToNextQuiz() {
        currentQuizIndex++;
    }

    public Game(List<Quiz> quizList) {
        this.quizList = quizList;
        this.gameState = GameState.WAITING;
    }

    public void changeState(GameState gameState) {
        this.gameState = gameState;
    }

    private List<AnswerSubmission> submissions = new ArrayList<>();

    public void submitAnswer(AnswerSubmission submission) {
        submissions.add(submission);
    }

    public List<AnswerSubmission> getSubmissionsForCurrentQuiz(long quizId) {
        return submissions.stream()
            .filter(s -> s.getQuizId() == quizId)
            .toList();
    }
}
