package com.danaleeband.guessit.service;

import com.danaleeband.guessit.model.dto.QuizCreateDTO;
import com.danaleeband.guessit.model.dto.QuizResponseDto;
import com.danaleeband.guessit.model.entity.Quiz;
import com.danaleeband.guessit.repository.QuizRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuizResponseDto getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new QuizResponseDto(
            quiz.getId(),
            quiz.getAnswer(),
            quiz.getHint1(),
            quiz.getHint2(),
            quiz.getHint3(),
            quiz.getHint4(),
            quiz.getHint5(),
            quiz.getHint6(),
            quiz.getCreatedAt(),
            quiz.getUpdatedAt()
        );
    }

    public void createQuiz(QuizCreateDTO quizCreateDTO) {
        Quiz quiz = Quiz.builder()
            .answer(quizCreateDTO.getAnswer())
            .hint1(quizCreateDTO.getHint1())
            .hint2(quizCreateDTO.getHint2())
            .hint3(quizCreateDTO.getHint3())
            .hint4(quizCreateDTO.getHint4())
            .hint5(quizCreateDTO.getHint5())
            .hint6(quizCreateDTO.getHint6())
            .build();

        quizRepository.save(quiz);
    }

    public List<Long> getRandomQuizzes(int limit) {
        List<Quiz> quizzes = quizRepository.findTop10ByOrderByRandom(); // 적절한 메소드 사용
        return quizzes.stream().map(Quiz::getId).collect(Collectors.toList());
    }
}
