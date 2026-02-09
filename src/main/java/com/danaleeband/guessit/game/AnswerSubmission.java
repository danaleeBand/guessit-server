package com.danaleeband.guessit.game;

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("answerSubmission")
@NoArgsConstructor
public class AnswerSubmission implements Serializable {

    private long playerId;
    private long quizId;
    private String answer;
    private String submittedAt;
    private boolean correct;

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
        this.submittedAt = Instant.now().toString();
    }
}
