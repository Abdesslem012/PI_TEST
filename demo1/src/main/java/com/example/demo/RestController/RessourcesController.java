package com.example.demo.RestController;

import com.example.demo.DTO.RessourcesDTO;
import com.example.demo.Entity.Ressources;
import com.example.demo.Repository.RessourcesRepository;
import com.example.demo.Service.LessonService;
import com.example.demo.Service.RessourcesService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/ressourcess", produces = MediaType.APPLICATION_JSON_VALUE)
public class RessourcesController {

    private final RessourcesService ressourcesService;
    private final LessonService lessonService;

    public RessourcesController(final RessourcesService ressourcesService, LessonService lessonService, RessourcesRepository ressourcesRepository) {
        this.ressourcesService = ressourcesService;
        this.lessonService = lessonService;
    }

    @PostMapping("/{lessonId}/ressources")
    public ResponseEntity<Ressources> addRessourceToLesson(@PathVariable Long lessonId, @RequestBody Ressources ressource) {
        Ressources savedRessource = ressourcesService.addRessourceToLesson(ressource, lessonId);
        return ResponseEntity.ok(savedRessource);
    }


    @GetMapping
    public ResponseEntity<List<RessourcesDTO>> getAllRessourcess() {
        return ResponseEntity.ok(ressourcesService.findAll());
    }

    @GetMapping("/{ressourcesId}")
    public ResponseEntity<RessourcesDTO> getRessources(
            @PathVariable(name = "ressourcesId") final Long ressourcesId) {
        return ResponseEntity.ok(ressourcesService.get(ressourcesId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRessources(
            @RequestBody @Valid final RessourcesDTO ressourcesDTO) {
        final Long createdRessourcesId = ressourcesService.create(ressourcesDTO);
        return new ResponseEntity<>(createdRessourcesId, HttpStatus.CREATED);
    }

    @PutMapping("/{ressourcesId}")
    public ResponseEntity<Long> updateRessources(
            @PathVariable(name = "ressourcesId") final Long ressourcesId,
            @RequestBody @Valid final RessourcesDTO ressourcesDTO) {
        ressourcesService.update(ressourcesId, ressourcesDTO);
        return ResponseEntity.ok(ressourcesId);
    }

    @DeleteMapping("/{ressourcesId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRessources(
            @PathVariable(name = "ressourcesId") final Long ressourcesId) {
        ressourcesService.delete(ressourcesId);
        return ResponseEntity.noContent().build();
    }

}
