package com.example.demo.RestController;

import com.example.demo.DTO.QuizDTO;
import com.example.demo.Service.QuizService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizController {
    private final QuizService quizService;

    public QuizController(final QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/assign-questions-to-quiz")
    public ResponseEntity<Void> assignQuestionsToQuizs(@RequestParam(name = "title") String title,
                                                       @RequestParam(name = "text") List<String> text) {

        quizService.assignQuestionsToQuizs(title,text);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/unassign-questions-from-quiz")
    public ResponseEntity<Void> unassignQuestionsFromQuiz(@RequestParam(name = "quizId") Long quizId,
                                                          @RequestParam(name = "questions") List<Long> questions) {

        quizService.unassignQuestionsFromQuiz(quizId, questions);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.findAll());
    }

    @PostMapping("/assign-random-questions/{quizId}")
    public void assignRandomQuestionsToQuiz(@PathVariable Long quizId) {
        // Appeler le service pour affecter des questions aléatoires au quiz spécifié par l'identifiant
        quizService.assignRandomQuestionsToQuiz(quizId);
    }

    @GetMapping("/loadFromJson")
    public String loadQuizFromJson() {
        try {
            quizService.insertQuizFromJson();
            return "Quiz loaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to load quiz";
        }
    }
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable(name = "quizId") final Long quizId) {
        return ResponseEntity.ok(quizService.get(quizId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createQuiz(@RequestBody @Valid final QuizDTO quizDTO) {
        final Long createdQuizId = quizService.create(quizDTO);
        return new ResponseEntity<>(createdQuizId, HttpStatus.CREATED);
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<Long> updateQuiz(@PathVariable(name = "quizId") final Long quizId,
                                           @RequestBody @Valid final QuizDTO quizDTO) {
        quizService.update(quizId, quizDTO);
        return ResponseEntity.ok(quizId);
    }

    @DeleteMapping("/{quizId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuiz(@PathVariable(name = "quizId") final Long quizId) {
        final ReferencedWarning referencedWarning = quizService.getReferencedWarning(quizId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        quizService.delete(quizId);
        return ResponseEntity.noContent().build();
    }

}


