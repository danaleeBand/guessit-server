package com.danaleeband.guessit.controller;

import com.danaleeband.guessit.model.entity.Quiz;
import com.danaleeband.guessit.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "퀴즈 조회", description = "id로 퀴즈 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Quiz.class))),
        @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    @GetMapping("/{id}")
    public Optional<Quiz> getQuizById(
        @Parameter(description = "ID of the quiz to be retrieved", required = true)
        @PathVariable Long id) {
        return quizService.getQuizById(id);
    }
}
