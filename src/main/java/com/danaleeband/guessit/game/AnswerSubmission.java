package com.danaleeband.guessit.game;

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("answerSubmission")
public class AnswerSubmission implements Serializable {

    private final long playerId;
    private final long quizId;
    private final String answer;
    private final Instant submittedAt;
    private final boolean correct;

    public AnswerSubmission(
        long playerId,
        long quizId,
        String answer,
        boolean correct
    ) {
        this.playerId = playerId;
        this.quizId = quizId;
        this.answer = answer;
        this.correct = correct;
        this.submittedAt = Instant.now();
    }
}
