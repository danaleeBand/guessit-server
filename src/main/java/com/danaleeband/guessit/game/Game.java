package com.danaleeband.guessit.game;

import com.danaleeband.guessit.global.GameState;
import com.danaleeband.guessit.quiz.Quiz;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("game")
public class Game implements Serializable {

    private List<Quiz> quizList;
    private GameState gameState;

    public Game() {
    }

    public Game(List<Quiz> quizList) {
        this.quizList = quizList;
        this.gameState = GameState.WAITING;
    }

    public void changeState(GameState gameState) {
        this.gameState = gameState;
    }
}
