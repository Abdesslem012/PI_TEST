package com.example.demo.RestController;

import com.example.demo.DTO.QuestionDTO;
import com.example.demo.Entity.Question;
import com.example.demo.Entity.Quiz;
import com.example.demo.Repository.QuizRepository;
import com.example.demo.Service.QuestionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionController {
    private final QuestionService questionService;

    private final QuizRepository quizRepository;

    public QuestionController(final QuestionService questionService, QuizRepository quizRepository) {
        this.questionService = questionService;
        this.quizRepository = quizRepository;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.findAll());
    }


    @GetMapping("/loadFromJson")
    public String loadQuestionsFromJson() {
        try {
            questionService.insertQuestionsFromJson();
            return "Questions loaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to load questions";
        }
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDTO> getQuestion(
            @PathVariable(name = "questionId") final Long questionId) {
        return ResponseEntity.ok(questionService.get(questionId));
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuestionsForQuiz(@PathVariable Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
        List<Question> questions = questionService.getQuestionsByQuiz(quiz);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createQuestion(@RequestBody @Valid final QuestionDTO questionDTO) {
        final Long createdQuestionId = questionService.create(questionDTO);
        return new ResponseEntity<>(createdQuestionId, HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Long> updateQuestion(
            @PathVariable(name = "questionId") final Long questionId,
            @RequestBody @Valid final QuestionDTO questionDTO) {
        questionService.update(questionId, questionDTO);
        return ResponseEntity.ok(questionId);
    }

    @DeleteMapping("/{questionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable(name = "questionId") final Long questionId) {
        questionService.delete(questionId);
        return ResponseEntity.noContent().build();
    }

}

