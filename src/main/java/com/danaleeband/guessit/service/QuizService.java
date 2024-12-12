package com.danaleeband.guessit.service;

import com.danaleeband.guessit.model.entity.Quiz;
import com.danaleeband.guessit.repository.QuizRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }
}
