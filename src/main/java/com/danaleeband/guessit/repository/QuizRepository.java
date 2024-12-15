package com.danaleeband.guessit.repository;

import com.danaleeband.guessit.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
