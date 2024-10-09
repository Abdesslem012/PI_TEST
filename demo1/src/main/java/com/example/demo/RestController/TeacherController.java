package com.example.demo.RestController;

import com.example.demo.DTO.TeacherDTO;
import com.example.demo.Service.TeacherService;
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
@RequestMapping(value = "/api/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(final TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.findAll());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDTO> getTeacher(
            @PathVariable(name = "teacherId") final Long teacherId) {
        return ResponseEntity.ok(teacherService.get(teacherId));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTeacher(@RequestBody @Valid final TeacherDTO teacherDTO) {
        final Long createdTeacherId = teacherService.create(teacherDTO);
        return new ResponseEntity<>(createdTeacherId, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{teacherId}")
    public ResponseEntity<Long> updateTeacher(
            @PathVariable(name = "teacherId") final Long teacherId,
            @RequestBody @Valid final TeacherDTO teacherDTO) {
        teacherService.update(teacherId, teacherDTO);
        return ResponseEntity.ok(teacherId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{teacherId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTeacher(
            @PathVariable(name = "teacherId") final Long teacherId) {
        teacherService.delete(teacherId);
        return ResponseEntity.noContent().build();
    }

}

