package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.entity.Quiz;
import java.util.List;
import java.util.Optional;

public interface QuizRepository {

    Optional<Quiz> findById(Long aLong);

    <S extends Quiz> S save(S entity);

    List<Quiz> findTop10ByOrderByRandom();

}
