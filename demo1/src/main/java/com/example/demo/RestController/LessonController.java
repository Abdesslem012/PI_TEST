package com.example.demo.RestController;

import com.example.demo.DTO.LessonDTO;
import com.example.demo.Entity.Lesson;
import com.example.demo.Repository.LessonRepository;
import com.example.demo.Repository.RessourcesRepository;
import com.example.demo.Repository.UnitRepository;
import com.example.demo.Service.LessonService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/lessons", produces = MediaType.APPLICATION_JSON_VALUE)
public class LessonController {
    private final LessonService lessonService;

    private final LessonRepository lessonRepository;

    private final RessourcesRepository ressourcesRepository;

    private final UnitRepository unitRepository;

    public LessonController(final LessonService lessonService, LessonRepository lessonRepository, RessourcesRepository ressourcesRepository, UnitRepository unitRepository) {
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
        this.ressourcesRepository = ressourcesRepository;
        this.unitRepository = unitRepository;
    }

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        return ResponseEntity.ok(lessonService.findAll());
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonDTO> getLesson(
            @PathVariable(name = "lessonId") final Long lessonId) {
        return ResponseEntity.ok(lessonService.get(lessonId));
    }

    /*@PostMapping("/{lessonId}/resources/{resourceId}")
    public ResponseEntity<?> assignResourceToLesson(@PathVariable Long lessonId, @PathVariable Long resourceId) {
        try {
            // Recherche de la leçon
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new NotFoundException("Lesson not found with ID: " + lessonId));

            // Recherche de la ressource
            Ressources resource = ressourcesRepository.findById(resourceId)
                    .orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));

            // Affectation de la ressource à la leçon
            lesson.getRessourcess().add(resource);
            lessonRepository.save(lesson);

            // Retournez une réponse appropriée
            return ResponseEntity.ok().body("Resource assigned successfully to lesson with ID: " + lessonId);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while assigning resource to lesson.");
        }
    }*/

    @PostMapping("/assign-ressource-to-lesson")
    public ResponseEntity<Void> assignResourceToLesson(@RequestParam(name = "description") String description, @RequestParam(name = "url") List<String> urls) {

        lessonService.assignRessourceToLesson(description,urls);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/lessons")
    public ResponseEntity<Optional<Lesson>> getLessonByDescription(@RequestParam(name = "description") String description) {
        Optional<Lesson> lesson = lessonService.findByDescription(description);
        return ResponseEntity.ok(lesson);
    }

    @PostMapping("/assign-rating-to-lesson")
    public ResponseEntity<Void> assignRatingToLesson(@RequestParam(name = "description") String description, @RequestParam(name = "nom") String nom) {

        lessonService.assignRatingToLesson(description,nom);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLesson(@RequestBody @Valid final LessonDTO lessonDTO) {
        final Long createdLessonId = lessonService.create(lessonDTO);
        return new ResponseEntity<>(createdLessonId, HttpStatus.CREATED);
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<Long> updateLesson(@PathVariable(name = "lessonId") final Long lessonId,
                                             @RequestBody @Valid final LessonDTO lessonDTO) {
        lessonService.update(lessonId, lessonDTO);
        return ResponseEntity.ok(lessonId);
    }

    @DeleteMapping("/{lessonId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLesson(@PathVariable(name = "lessonId") final Long lessonId) {
        final ReferencedWarning referencedWarning = lessonService.getReferencedWarning(lessonId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        lessonService.delete(lessonId);
        return ResponseEntity.noContent().build();
    }


}

