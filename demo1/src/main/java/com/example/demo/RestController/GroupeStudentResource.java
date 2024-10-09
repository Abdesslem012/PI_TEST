package com.example.demo.RestController;


import com.example.demo.DTO.GroupeStudentDTO;
import com.example.demo.Entity.Student;
import com.example.demo.Service.GroupeStudentService;
import com.example.demo.Service.StudentService;
import com.example.demo.util.ReferencedException;
import com.example.demo.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/groupeStudents", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupeStudentResource {

    private final GroupeStudentService groupeStudentService;
    private final StudentService studentService;

    public GroupeStudentResource(final GroupeStudentService groupeStudentService, StudentService studentService) {
        this.groupeStudentService = groupeStudentService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<GroupeStudentDTO>> getAllGroupeStudents() {
        return ResponseEntity.ok(groupeStudentService.findAll());
    }

    @GetMapping("/{groupstudentId}")
    public ResponseEntity<GroupeStudentDTO> getGroupeStudent(
            @PathVariable(name = "groupstudentId") final Long groupstudentId) {
        return ResponseEntity.ok(groupeStudentService.get(groupstudentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGroupeStudent(
            @RequestBody @Valid final GroupeStudentDTO groupeStudentDTO) {
        final Long createdGroupstudentId = groupeStudentService.create(groupeStudentDTO);
        return new ResponseEntity<>(createdGroupstudentId, HttpStatus.CREATED);
    }

    @PutMapping("/{groupstudentId}")
    public ResponseEntity<Long> updateGroupeStudent(
            @PathVariable(name = "groupstudentId") final Long groupstudentId,
            @RequestBody @Valid final GroupeStudentDTO groupeStudentDTO) {
        groupeStudentService.update(groupstudentId, groupeStudentDTO);
        return ResponseEntity.ok(groupstudentId);
    }

    @DeleteMapping("/{groupstudentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGroupeStudent(
            @PathVariable(name = "groupstudentId") final Long groupstudentId) {
        final ReferencedWarning referencedWarning = groupeStudentService.getReferencedWarning(groupstudentId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        groupeStudentService.delete(groupstudentId);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{nomGroupe}/assignStudents")
    public ResponseEntity<Void> assignStudentsToGroup(
            @PathVariable(name = "nomGroupe") String nomGroupe,
            @RequestBody Set<Student> studentId) {
        groupeStudentService.assignStudentsToGroup(nomGroupe, studentId);
        return ResponseEntity.ok().build();
    }











}
