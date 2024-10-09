 /*package com.example.demo.RestController;

import com.example.demo.DTO.ClassesDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.Service.ClassesService;
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

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/classess", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassesController {

    private final ClassesService classesService;

    public ClassesController(final ClassesService classesService) {
        this.classesService = classesService;
    }
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ClassesDTO>> getAllClassess() {
        return ResponseEntity.ok(classesService.findAll());
    }

    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @GetMapping("/{classeId}")
    public ResponseEntity<ClassesDTO> getClasses(
            @PathVariable(name = "classeId") final Long classeId) {
        return ResponseEntity.ok(classesService.get(classeId));
    }

    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createClasses(@RequestBody @Valid final ClassesDTO classesDTO) {
        final Long createdClasseId = classesService.create(classesDTO);
        return new ResponseEntity<>(createdClasseId, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{classeId}")
    public ResponseEntity<Long> updateClasses(@PathVariable(name = "classeId") final Long classeId,
                                              @RequestBody @Valid final ClassesDTO classesDTO) {
        classesService.update(classeId, classesDTO);
        return ResponseEntity.ok(classeId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{classeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClasses(
            @PathVariable(name = "classeId") final Long classeId) {
        final ReferencedWarning referencedWarning = classesService.getReferencedWarning(classeId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        classesService.delete(classeId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/students")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> addStudentToClass(
            @RequestParam(name = "className") final String className,
            @RequestParam(name = "studentFirstName") final String studentFirstName,
            @RequestParam(name = "studentLastName") final String studentLastName) {
        classesService.addStudentToClass(className, studentFirstName, studentLastName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{className}/assign-all-students")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> assignAllStudentsToClass(@PathVariable(name = "className") String className) {
        classesService.assignAllStudentsToClass(className);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @PostMapping("/assign-student")
    public ResponseEntity<Void> assignStudentToClass(@RequestParam(name = "className") String className,
                                                     @RequestParam(name = "studentName") String studentName) {
        String[] studentNameParts = studentName.split(" ");
        String studentFirstName = studentNameParts[0];
        String studentLastName = studentNameParts[1];

        classesService.assignStudentToClass(className, studentFirstName, studentLastName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getClassStudents() {
        List<StudentDTO> students = classesService.getAllStudentsWithClasses();
        return ResponseEntity.ok(students);
    }


    @GetMapping("/classess/{className}/assigned-data")
    public ResponseEntity<List<StudentDTO>> getAssignedDataForClass(@PathVariable("className") String className) {
        // Récupérer les données des étudiants assignés à la classe spécifiée
        List<StudentDTO> assignedStudents = classesService.getAssignedStudentsForClass(className);
        return ResponseEntity.ok(assignedStudents);
    }


}
*/