package com.danaleeband.guessit.controller;

import com.danaleeband.guessit.controller.dto.QuizCreateDto;
import com.danaleeband.guessit.controller.dto.QuizResponseDto;
import com.danaleeband.guessit.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quizzes")
@Tag(name = "퀴즈")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "퀴즈 조회", description = "id로 퀴즈 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponseDto.class)))
    })
    public QuizResponseDto getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @PostMapping()
    @Operation(summary = "퀴즈 생성", description = "퀴즈 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuiz(@RequestBody @Valid QuizCreateDto quizCreateDTO) {
        quizService.createQuiz(quizCreateDTO);
    }

}
