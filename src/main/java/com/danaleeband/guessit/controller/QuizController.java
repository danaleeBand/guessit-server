package com.danaleeband.guessit.controller;

import com.danaleeband.guessit.model.entity.Quiz;
import com.danaleeband.guessit.service.QuizService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{id}")
    public Optional<Quiz> getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }
}
