package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.entity.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q ORDER BY random() LIMIT 10")
    List<Quiz> findTop10ByOrderByRandom();
}
