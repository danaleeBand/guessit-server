package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.entity.Quiz;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizJpaRepository extends QuizRepository, JpaRepository<Quiz, Long> {

    @Override
    Optional<Quiz> findById(Long aLong);

    @Override
    <S extends Quiz> S save(S entity);

    @Override
    @Query("SELECT q FROM Quiz q ORDER BY random() LIMIT 10")
    List<Quiz> findTop10ByOrderByRandom();
}
