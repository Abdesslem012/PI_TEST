package com.example.demo.RestController;

import com.example.demo.DTO.PresenceDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.Service.PresenceService;

import com.example.demo.Service.StudentService;
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
@RequestMapping(value = "/api/presences", produces = MediaType.APPLICATION_JSON_VALUE)
public class PresenceController {

    private final PresenceService presenceService;
    private final StudentService studentService;

    public PresenceController(final PresenceService presenceService,final StudentService studentService) {
        this.presenceService = presenceService;
        this.studentService= studentService;
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PresenceDTO>> getAllPresences() {
        return ResponseEntity.ok(presenceService.findAll());
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping("/{presenceId}")
    public ResponseEntity<PresenceDTO> getPresence(
            @PathVariable(name = "presenceId") final Long presenceId) {
        return ResponseEntity.ok(presenceService.get(presenceId));
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPresence(@RequestBody @Valid final PresenceDTO presenceDTO) {
        final Long createdPresenceId = presenceService.create(presenceDTO);
        return new ResponseEntity<>(createdPresenceId, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{presenceId}")
    public ResponseEntity<Long> updatePresence(
            @PathVariable(name = "presenceId") final Long presenceId,
            @RequestBody @Valid final PresenceDTO presenceDTO) {
        presenceService.update(presenceId, presenceDTO);
        return ResponseEntity.ok(presenceId);
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @DeleteMapping("/{presenceId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePresence(
            @PathVariable(name = "presenceId") final Long presenceId) {
        presenceService.delete(presenceId);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.findAll(); // Remplacez 'studentService' par votre service d'étudiants
        return ResponseEntity.ok(students);
    }


}
