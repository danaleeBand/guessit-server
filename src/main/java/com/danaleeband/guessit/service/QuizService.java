package com.danaleeband.guessit.service;

import com.danaleeband.guessit.model.dto.QuizCreateDTO;
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

    public Quiz createQuiz(QuizCreateDTO quizCreateDTO) {
        Quiz quiz = new Quiz();
        quiz.setAnswer(quizCreateDTO.getAnswer());
        quiz.setHint1(quizCreateDTO.getHint1());
        quiz.setHint2(quizCreateDTO.getHint2());
        quiz.setHint3(quizCreateDTO.getHint3());
        quiz.setHint4(quizCreateDTO.getHint4());
        quiz.setHint5(quizCreateDTO.getHint5());
        quiz.setHint6(quizCreateDTO.getHint6());

        return quizRepository.save(quiz);
    }
}
