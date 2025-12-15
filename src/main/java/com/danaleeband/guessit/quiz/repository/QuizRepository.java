package com.danaleeband.guessit.quiz.repository;

import com.danaleeband.guessit.quiz.Quiz;
import java.util.List;
import java.util.Optional;

public interface QuizRepository {

    Optional<Quiz> findById(Long aLong);

    <S extends Quiz> S save(S entity);

    List<Quiz> findTop10ByOrderByRandom();

}
