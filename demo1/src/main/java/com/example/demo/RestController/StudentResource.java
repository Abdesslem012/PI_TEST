package com.example.demo.RestController;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.Entity.Student;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.StudentService;
import com.example.demo.util.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentResource {

    private final StudentService studentService;
    private final EmailService emailService;

    public StudentResource(final StudentService studentService, EmailService emailService) {
        this.studentService = studentService;
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudent(
            @PathVariable(name = "studentId") final Long studentId) {
        return ResponseEntity.ok(studentService.get(studentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createStudent(@RequestBody @Valid final StudentDTO studentDTO) {
        final Long createdStudentId = studentService.create(studentDTO);
        return new ResponseEntity<>(createdStudentId, HttpStatus.CREATED);
    }


    @PutMapping("/{studentId}")
    public ResponseEntity<Long> updateStudent(
            @PathVariable(name = "studentId") final Long studentId,
            @RequestBody @Valid final StudentDTO studentDTO) {
        try {
            studentService.update(studentId, studentDTO);
            return ResponseEntity.ok(studentId);
        } catch (NotFoundException e) {
            // Gérer l'erreur de données non trouvées (par exemple, étudiant non trouvé)
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Gérer toute autre erreur inattendue
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @DeleteMapping("/{studentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable(name = "studentId") final Long studentId) {
        studentService.delete(studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students")
    public ModelAndView searchStudents(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Long classId) {

        List<Student> students = studentService.findByFilters(firstName, lastName, classId);

        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", students);

        return modelAndView;
    }

    @PutMapping("/{firstName}/{lastName}/assign-class/{className}")
    public ResponseEntity<String> assignStudentToClass(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @PathVariable String className) {
        try {
            Student student = studentService.assignStudentToClass(firstName, lastName, className);
            sendEmailToStudent(student, className); // Appel de la méthode pour envoyer l'e-mail
            return ResponseEntity.ok("Student " + student.getFirstName() + " " + student.getLastName() +
                    " assigned to class " + className + " successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    private void sendEmailToStudent(Student student, String className) {
        try {
            String emailContent = "Bonjour " + student.getFirstName() + ","
                    + "Vous avez été assigné à la classe " + className + "."
                    + "Cordialement,Courzello";

            emailService.sendSimpleEmail(student.getEmail(), "Affectation à la classe", emailContent);
        } catch (Exception e) {
            // Gérer les exceptions d'envoi d'e-mail ici
            System.out.println("Erreur lors de l'envoi de l'e-mail à l'étudiant : " + e.getMessage());
        }
    }

}
