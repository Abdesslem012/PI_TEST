package com.example.demo.RestController;

import com.example.demo.DTO.BadgeDTO;
import com.example.demo.Entity.Badge;
import com.example.demo.Entity.Quiz;
import com.example.demo.Service.BadgeService;
import com.example.demo.Service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/badge", produces = MediaType.APPLICATION_JSON_VALUE)
public class BadgeController {
    private final QuizService quizService;

    private final BadgeService badgeService;

    public BadgeController(QuizService quizService, BadgeService badgeService) {
        this.quizService = quizService;
        this.badgeService = badgeService;
    }

    @GetMapping
    public ResponseEntity<List<Badge>> getAllBadges() {
        return ResponseEntity.ok(badgeService.getAllBadges());
    }

    @GetMapping("/{badgeId}")
    public ResponseEntity<BadgeDTO> getBadges(@PathVariable(name = "badgeId") final Long badgeId) {
        return ResponseEntity.ok(badgeService.get(badgeId));
    }

    // Endpoint pour récupérer tous les quizzes
    @GetMapping("/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    // Endpoint pour récupérer un quiz par son ID
    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz != null) {
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/loadFromJson")
    public String loadQuizFromJson() {
        try {
            badgeService.insertBadgeFromJson();
            return "Badge loaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to load badge";
        }
    }

    // Endpoint pour associer des badges à un quiz
    @PostMapping("/quizzes/{quizId}/badges")
    public ResponseEntity<Void> assignBadgesToQuiz(@PathVariable Long quizId, @RequestBody Set<Badge> badges) {
        quizService.assignBadgesToQuiz(quizId, badges);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Endpoint pour attribuer des badges à un étudiant
    @PostMapping("/students/{studentId}/badges")
    public ResponseEntity<Void> assignBadgesToStudent(@PathVariable Long studentId, @RequestBody List<Badge> badges) {
        badgeService.assignBadgesToStudent(studentId, badges);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}


