package com.danaleeband.guessit.controller;

import com.danaleeband.guessit.model.dto.QuizCreateDTO;
import com.danaleeband.guessit.model.entity.Quiz;
import com.danaleeband.guessit.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Operation(summary = "퀴즈 조회", description = "id로 퀴즈 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Quiz.class)))
    })
    @GetMapping("/{id}")
    public Optional<Quiz> getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @Operation(summary = "퀴즈 생성", description = "퀴즈 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공")
    })
    @PostMapping()
    public ResponseEntity<Quiz> createQuiz(@RequestBody @Valid QuizCreateDTO quizCreateDTO) {
        Quiz createdQuiz = quizService.createQuiz(quizCreateDTO);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

}
