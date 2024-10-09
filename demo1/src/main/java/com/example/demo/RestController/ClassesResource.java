package com.example.demo.RestController;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.DTO.ClassesDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.Entity.Classes;
import com.example.demo.Entity.ClassesStats;
import com.example.demo.Service.ClassesService;
import com.example.demo.Service.StudentService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/classess", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClassesResource {
    private static final Logger logger = LoggerFactory.getLogger(ClassesResource.class);

    private final ClassesService classesService;

    public ClassesResource(final ClassesService classesService, StudentService studentService) {
        this.classesService = classesService;
    }

    @GetMapping
    public ResponseEntity<List<ClassesDTO>> getAllClassess() {
        return ResponseEntity.ok(classesService.findAll());
    }

    @GetMapping("/{classeId}")
    public ResponseEntity<ClassesDTO> getClasses(
            @PathVariable(name = "classeId") final String classeId) {
        return ResponseEntity.ok(classesService.get(classeId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createClasses(@RequestBody @Valid final ClassesDTO classesDTO, HttpServletRequest request) {
        // Log the incoming request's content type
        logger.info("Received request with Content-Type: {}", request.getContentType());

        // Log the data being processed (if required)
        logger.debug("Received data: {}", classesDTO);

        // Proceed with the existing logic
        final String createdClasseId = classesService.create(classesDTO);
        return new ResponseEntity<>(createdClasseId, HttpStatus.CREATED);
    }


    @PutMapping("/{classeId}")
    public ResponseEntity<String> updateClasses(@PathVariable(name = "classeId") final String classeId,
                                              @RequestBody @Valid final ClassesDTO classesDTO) {
        classesService.update(classeId, classesDTO);
        return ResponseEntity.ok(classeId);
    }

    @DeleteMapping("/{classeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClasses(
            @PathVariable(name = "classeId") final String classeId) {
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

  /*  @GetMapping("/classes")
    public List<Classes> findClassesByCriteria(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        return classesService.findClassesByCriteria(name, description);
    }
/*
    @GetMapping("/classes/stats")
    public List<ClassesStats> getClassesStats() {
        return classesService.getClassesStats();
    }
*/


}
