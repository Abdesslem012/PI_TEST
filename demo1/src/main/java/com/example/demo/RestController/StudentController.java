/*package com.example.demo.RestController;

import com.example.demo.DTO.StudentDTO;
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
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {
    private final StudentService studentService;

    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudent(
            @PathVariable(name = "studentId") final Long studentId) {
        return ResponseEntity.ok(studentService.get(studentId));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createStudent(@RequestBody @Valid final StudentDTO studentDTO) {
        final Long createdStudentId = studentService.create(studentDTO);
        return new ResponseEntity<>(createdStudentId, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{studentId}")
    public ResponseEntity<Long> updateStudent(
            @PathVariable(name = "studentId") final Long studentId,
            @RequestBody @Valid final StudentDTO studentDTO) {
        studentService.update(studentId, studentDTO);
        return ResponseEntity.ok(studentId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{studentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable(name = "studentId") final Long studentId) {
        studentService.delete(studentId);
        return ResponseEntity.noContent().build();
    }

}*/

